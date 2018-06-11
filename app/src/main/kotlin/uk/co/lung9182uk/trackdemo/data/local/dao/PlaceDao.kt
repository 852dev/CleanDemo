package uk.co.lung9182uk.trackdemo.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import uk.co.lung9182uk.trackdemo.data.local.DBConstants
import uk.co.lung9182uk.trackdemo.data.local.entity.PlaceEntity

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(vararg entity: PlaceEntity)

    @Query(DBConstants.QUERY_PLACE)
    fun getLastSearchPlace(): PlaceEntity?

}