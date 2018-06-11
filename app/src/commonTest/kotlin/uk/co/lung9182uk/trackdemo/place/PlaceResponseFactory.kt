package uk.co.lung9182uk.trackdemo.place

import uk.co.lung9182uk.trackdemo.DataFactory.Companion.randomDouble
import uk.co.lung9182uk.trackdemo.DataFactory.Companion.randomString
import uk.co.lung9182uk.trackdemo.data.remote.response.GeometryResponse
import uk.co.lung9182uk.trackdemo.data.remote.response.LocationResponse
import uk.co.lung9182uk.trackdemo.data.remote.response.PlaceResponse

class PlaceResponseFactory {

    companion object {

        fun makePlaceResponseList(count: Int): List<PlaceResponse> {
            val list = mutableListOf<PlaceResponse>()
            repeat(count) {
                list.add(makePlaceResponse())
            }
            return list
        }

        fun makePlaceResponse(): PlaceResponse {
            return PlaceResponse(name = randomString(),
                    geometry = makeGeometryResponse(),
                    formattedAddress = randomString(),
                    types = emptyList(),
                    icon = randomString())
        }

        fun makeGeometryResponse(): GeometryResponse {
            return GeometryResponse(location = makeLocationResponse())
        }

        fun makeLocationResponse(): LocationResponse {
            return LocationResponse(lat = randomDouble(),
                    lng = randomDouble())
        }

    }

}