package uk.co.lung9182uk.trackdemo.data.remote.api

import io.reactivex.Flowable
import retrofit2.http.*
import uk.co.lung9182uk.trackdemo.data.remote.ApiConstants
import uk.co.lung9182uk.trackdemo.data.remote.response.AutoCompleteResponse
import uk.co.lung9182uk.trackdemo.data.remote.response.PlaceBaseResponse

interface PlaceApiService {

    @GET("autocomplete/json")
    fun getAutoComplete(@Query("input") input: String, @Query("key") key: String = ApiConstants.PLACE_API_KEY): Flowable<AutoCompleteResponse>

    @GET("details/json")
    fun getPlace(@Query("placeid") placeId: String, @Query("key") key: String= ApiConstants.PLACE_API_KEY): Flowable<PlaceBaseResponse>

    @GET("nearbysearch/json")
    fun getNearby(@Query("location") location: String, @Query("radius") radius: String, @Query("keyword") keyword: String, @Query("key") key: String= ApiConstants.PLACE_API_KEY): Flowable<PlaceBaseResponse>

}