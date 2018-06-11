package uk.co.lung9182uk.trackdemo.data.local.converter

import android.arch.persistence.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LatlngConverter {

    @TypeConverter
    fun fromString(value: String): LatLng = Gson().fromJson<LatLng>(value, object : TypeToken<LatLng>() {}.type)

    @TypeConverter
    fun fromLatLng(latLng: LatLng): String = Gson().toJson(latLng)

}