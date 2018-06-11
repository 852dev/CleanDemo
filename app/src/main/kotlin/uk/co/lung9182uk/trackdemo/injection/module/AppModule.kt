package uk.co.lung9182uk.trackdemo.injection.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import uk.co.lung9182uk.trackdemo.data.local.AppDatabase
import uk.co.lung9182uk.trackdemo.data.mapper.PlaceMapper
import uk.co.lung9182uk.trackdemo.data.remote.api.PlaceApiService
import uk.co.lung9182uk.trackdemo.data.repository.PlaceRepositoryImpl
import uk.co.lung9182uk.trackdemo.domain.repository.PlaceRepository
import uk.co.lung9182uk.trackdemo.util.NetworkUtils
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext,
                AppDatabase::class.java, "trackdemo.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun providePlaceRepository(service: PlaceApiService, mapper: PlaceMapper, appDatabase: AppDatabase, networkUtils: NetworkUtils): PlaceRepository = PlaceRepositoryImpl(service, mapper, appDatabase, networkUtils)

}