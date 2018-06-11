package uk.co.lung9182uk.trackdemo.data.local

import android.arch.persistence.room.Room
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import uk.co.lung9182uk.trackdemo.place.PlaceEntityFactory
import uk.co.lung9182uk.trackdemo.RxSchedulersOverrideRule
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class AppDatabaseTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    private lateinit var appDatabase: AppDatabase

    @Before
    fun initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application, AppDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun verifyGetLastSearchPlace() {
        val entities = PlaceEntityFactory.makePlaceEntityList(10)
        val expectLastEntity = entities.last()
        entities.forEach {
            appDatabase.placeDao().insertPlace(it)
        }
        val lastEntity = appDatabase.placeDao().getLastSearchPlace()
        assertEquals(expectLastEntity.createTime, lastEntity!!.createTime)
    }
}