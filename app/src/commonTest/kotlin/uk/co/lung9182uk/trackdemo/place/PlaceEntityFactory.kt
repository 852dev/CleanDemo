package uk.co.lung9182uk.trackdemo.place

import com.google.android.gms.maps.model.LatLng
import uk.co.lung9182uk.trackdemo.DataFactory.Companion.randomDouble
import uk.co.lung9182uk.trackdemo.DataFactory.Companion.randomString
import uk.co.lung9182uk.trackdemo.data.local.entity.PlaceEntity

class PlaceEntityFactory {

    companion object {

        fun makePlaceEntityList(count: Int): List<PlaceEntity> {
            val list = mutableListOf<PlaceEntity>()
            repeat(count) {
                val currentTime = System.currentTimeMillis()
                list.add(makePlaceEntity(currentTime))
            }
            return list
        }

        fun makePlaceEntity(createTime: Long): PlaceEntity {
            return PlaceEntity(name = randomString(),
                    location = LatLng(randomDouble(), randomDouble()),
                    formattedAddress = randomString(),
                    types = emptyList(),
                    icon = randomString(),
                    createTime = createTime)
        }

    }

}