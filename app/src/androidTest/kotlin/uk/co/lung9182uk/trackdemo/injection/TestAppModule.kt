package uk.co.lung9182uk.trackdemo.injection

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import uk.co.lung9182uk.trackdemo.data.local.AppDatabase
import uk.co.lung9182uk.trackdemo.domain.repository.PlaceRepository
import javax.inject.Singleton

@Module
class TestAppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application


    @Provides
    @Singleton
    internal fun providAppDatabase(application: Application): AppDatabase {
        return Room.inMemoryDatabaseBuilder(application, AppDatabase::class.java).build()
    }

    @Provides
    @Singleton
    fun providePlaceRepository(): PlaceRepository = mock()

}