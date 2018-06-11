package uk.co.lung9182uk.trackdemo.data.mapper

import com.google.android.gms.maps.model.LatLng
import uk.co.lung9182uk.trackdemo.data.local.entity.PlaceEntity
import uk.co.lung9182uk.trackdemo.data.remote.response.*
import uk.co.lung9182uk.trackdemo.domain.model.*
import javax.inject.Inject

class PlaceMapper @Inject constructor() {

    fun toPredictions(from: AutoCompleteResponse): List<Prediction> {
        return from.predictions.map {
            Prediction(
                    id = it.id,
                    placeId = it.placeId,
                    description = it.description,
                    reference = it.reference
            )
        }
    }

    fun toPlace(from: PlaceBaseResponse): Place {
        return toPlace(from.result)
    }

    fun toPlaces(from: PlaceBaseResponse): List<Place> {
        val placeResponse = from.results
        return placeResponse.map {
            toPlace(it)
        }
    }

    fun toPlace(from: PlaceResponse): Place {
        return Place(location = LatLng(from.geometry.location.lat, from.geometry.location.lng),
                name = from.name + "",
                formattedAddress = from.formattedAddress + "",
                types = if (from.types == null) emptyList() else from.types!!,
                icon = from.icon + "")
    }

    fun toPlace(from: PlaceEntity): Place {
        return Place(location = from.location,
                name = from.name,
                formattedAddress = from.formattedAddress,
                types = from.types,
                icon = from.icon)
    }

    fun toPlaceEntity(from: Place, createTime: Long): PlaceEntity {
        return PlaceEntity(location = from.location,
                name = from.name,
                formattedAddress = from.formattedAddress,
                types = from.types,
                icon = from.icon,
                createTime = createTime)
    }

}