package uk.co.lung9182uk.trackdemo.domain.interactor

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import uk.co.lung9182uk.trackdemo.domain.model.Place
import uk.co.lung9182uk.trackdemo.domain.model.Prediction
import uk.co.lung9182uk.trackdemo.domain.repository.PlaceRepository

class MapInteractor @Inject constructor(private val placeRepository: PlaceRepository) {

    fun checkNetwork(): Single<Boolean> {
        return placeRepository.isNetworkConnected()
    }

    fun getAutoComplete(input: String): Flowable<List<Prediction>> {
        return fetchAfterCheckNetwork(placeRepository.getAutoComplete(input))
    }

    fun getPlaceDetail(placeId: String): Flowable<Place> {
        return fetchAfterCheckNetwork(placeRepository.getPlace(placeId))
    }

    fun searchNearby(location: String, radius: String, keyword: String): Flowable<List<Place>> {
        return fetchAfterCheckNetwork(placeRepository.getNearby(location, radius, keyword))
    }

    private fun <T> fetchAfterCheckNetwork(flowable: Flowable<T>): Flowable<T> {
        return placeRepository.isNetworkConnected().flatMapPublisher {
            if (it) {
                flowable
            } else {
                Flowable.error(Throwable("No network connected!"))
            }
        }
    }

    fun getLastSearchPlace(): Maybe<Place> {
        return placeRepository.getLastQuery()
    }


}