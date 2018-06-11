package uk.co.lung9182uk.trackdemo.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import uk.co.lung9182uk.trackdemo.data.local.dao.PlaceDao
import uk.co.lung9182uk.trackdemo.data.local.entity.PlaceEntity

@Database(entities = [(PlaceEntity::class)], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}