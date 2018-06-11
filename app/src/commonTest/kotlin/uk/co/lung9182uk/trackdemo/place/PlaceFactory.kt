package uk.co.lung9182uk.trackdemo.place

import com.google.android.gms.maps.model.LatLng
import uk.co.lung9182uk.trackdemo.DataFactory.Companion.randomDouble
import uk.co.lung9182uk.trackdemo.DataFactory.Companion.randomString
import uk.co.lung9182uk.trackdemo.domain.model.Place

class PlaceFactory {

    companion object {

        fun makePlaceList(count: Int): List<Place> {
            val list = mutableListOf<Place>()
            repeat(count) {
                list.add(makePlace())
            }
            return list
        }

        fun makePlace(): Place {
            return Place(name = randomString(),
                    location = LatLng(randomDouble(), randomDouble()),
                    formattedAddress = randomString(),
                    types = emptyList(),
                    icon = randomString())
        }

    }

}