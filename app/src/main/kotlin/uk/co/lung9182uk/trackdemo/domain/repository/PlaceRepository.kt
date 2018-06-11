package uk.co.lung9182uk.trackdemo.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import uk.co.lung9182uk.trackdemo.domain.model.Place
import uk.co.lung9182uk.trackdemo.domain.model.Prediction

interface PlaceRepository {
    fun isNetworkConnected(): Single<Boolean>
    fun getAutoComplete(input: String): Flowable<List<Prediction>>
    fun getPlace(placeId: String): Flowable<Place>
    fun getNearby(location: String, radius: String, keyword: String): Flowable<List<Place>>
    fun getLastQuery(): Maybe<Place>
    fun savePlace(place:Place): Completable
}