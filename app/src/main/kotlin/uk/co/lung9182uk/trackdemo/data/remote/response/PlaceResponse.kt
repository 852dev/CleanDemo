package uk.co.lung9182uk.trackdemo.data.remote.response

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
        var geometry: GeometryResponse,
        @SerializedName("formatted_address")
        val formattedAddress: String?,
        var name: String,
        var types: List<String>?,
        var icon:String?)

data class GeometryResponse(
        var location: LocationResponse)

data class LocationResponse(
        var lat: Double,
        var lng: Double)
