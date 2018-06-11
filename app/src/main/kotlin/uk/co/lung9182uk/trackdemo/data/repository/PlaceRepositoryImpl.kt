package uk.co.lung9182uk.trackdemo.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import uk.co.lung9182uk.trackdemo.data.local.AppDatabase
import uk.co.lung9182uk.trackdemo.data.mapper.PlaceMapper
import uk.co.lung9182uk.trackdemo.data.remote.api.PlaceApiService
import uk.co.lung9182uk.trackdemo.domain.model.Place
import uk.co.lung9182uk.trackdemo.domain.model.Prediction
import uk.co.lung9182uk.trackdemo.domain.repository.PlaceRepository
import uk.co.lung9182uk.trackdemo.util.NetworkUtils

class PlaceRepositoryImpl(private var placeApiService: PlaceApiService,
                          private var placeMapper: PlaceMapper,
                          private var appDatabase: AppDatabase,
                          private var networkUtils: NetworkUtils) : PlaceRepository {


    override fun isNetworkConnected(): Single<Boolean> {
        return Single.just(networkUtils.isNetworkConnected())
    }

    override fun getAutoComplete(input: String): Flowable<List<Prediction>> {
        return placeApiService.getAutoComplete(input).map {
            placeMapper.toPredictions(it)
        }
    }

    override fun getPlace(placeId: String): Flowable<Place> {
        return placeApiService.getPlace(placeId).map {
            placeMapper.toPlace(it)
        }.flatMap {
            savePlace(it).toSingle { it }.toFlowable()
        }
    }

    override fun getNearby(location: String, radius: String, keyword: String): Flowable<List<Place>> {
        return placeApiService.getNearby(location, radius, keyword).map {
            placeMapper.toPlaces(it)
        }
    }

    override fun getLastQuery(): Maybe<Place> {
        return Maybe.defer<Place> {
            val lastPlaceEntity = appDatabase.placeDao().getLastSearchPlace()
            if (lastPlaceEntity != null) {
                val lastPlace = placeMapper.toPlace(lastPlaceEntity!!)
                Maybe.just<Place>(lastPlace)
            } else {
                Maybe.empty()
            }
        }
    }

    override fun savePlace(place: Place): Completable {
        return Completable.defer {
            val currentTime = System.currentTimeMillis()
            appDatabase.placeDao().insertPlace(placeMapper.toPlaceEntity(place, currentTime))
            Completable.complete()
        }
    }

}

