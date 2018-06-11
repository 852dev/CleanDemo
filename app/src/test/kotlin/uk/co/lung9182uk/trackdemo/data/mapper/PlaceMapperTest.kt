package uk.co.lung9182uk.trackdemo.data.mapper

import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import uk.co.lung9182uk.trackdemo.place.PlaceResponseFactory
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class PlaceMapperTest {

    private lateinit var placeMapper: PlaceMapper

    @Before
    fun setUp() {
        placeMapper = PlaceMapper()
    }

    @Test
    fun verifyMapResponse2Entity() {
        val placeResponse = PlaceResponseFactory.makePlaceResponse()
        val placeEntity = placeMapper.toPlace(placeResponse)
        assertEquals(placeResponse.name, placeEntity.name)
        assertEquals(placeResponse.formattedAddress, placeEntity.formattedAddress)
        assertEquals(placeResponse.geometry.location.lat, placeEntity.location.latitude)
        assertEquals(placeResponse.geometry.location.lng, placeEntity.location.longitude)
        assertEquals(placeResponse.icon, placeEntity.icon)
        assertEquals(placeResponse.types, placeEntity.types)
        assertEquals(placeResponse.icon, placeEntity.icon)
    }

}