package uk.co.lung9182uk.trackdemo.data.remote.response

import com.google.gson.annotations.SerializedName

data class AutoCompleteResponse(var predictions: List<PredictionResponse>)

data class PredictionResponse(
        var description: String,
        var id: String,
        @SerializedName("place_id")
        val placeId: String,
        var reference: String)
