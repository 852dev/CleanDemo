package uk.co.lung9182uk.trackdemo.data.local.entity

import android.arch.persistence.room.*
import com.google.android.gms.maps.model.LatLng
import uk.co.lung9182uk.trackdemo.data.local.DBConstants
import uk.co.lung9182uk.trackdemo.data.local.converter.LatlngConverter
import uk.co.lung9182uk.trackdemo.data.local.converter.StringListConverter

@Entity(tableName = DBConstants.TABLE_PLACE)
@TypeConverters(LatlngConverter::class, StringListConverter::class)
data class PlaceEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long= 0,
        var name: String,
        var location: LatLng,
        @ColumnInfo(name = "formatted_address")
        var formattedAddress: String,
        var types: List<String>,
        var icon: String,
        @ColumnInfo(name = "create_time")
        var createTime: Long
)