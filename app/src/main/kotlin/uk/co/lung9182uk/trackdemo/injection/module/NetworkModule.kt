package uk.co.lung9182uk.trackdemo.injection.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.lung9182uk.trackdemo.BuildConfig
import uk.co.lung9182uk.trackdemo.data.remote.ApiConstants
import uk.co.lung9182uk.trackdemo.data.remote.api.PlaceApiService
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    @Named("placeEndpoint")
    fun placeEndpoint() = ApiConstants.PLACE_ENDPOINT

    @Singleton
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(logging)
            okHttpBuilder.addNetworkInterceptor(StethoInterceptor())
        }
        return okHttpBuilder.apply {
            readTimeout(600.toLong(), TimeUnit.SECONDS)
            connectTimeout(600.toLong(), TimeUnit.SECONDS)
        }
    }

    @Singleton
    @Provides
    @Named("directionRetrofit")
    fun provideDirectionEndpoint(retrofitBuilder: Retrofit.Builder,
                                 okHttpClientBuilder: OkHttpClient.Builder,
                                 @Named("directionEndpoint") baseUrl: String): Retrofit {
        return retrofitBuilder
                .client(okHttpClientBuilder.build())
                .baseUrl(baseUrl)
                .build()
    }

    @Singleton
    @Provides
    @Named("placeRetrofit")
    fun providePlaceEndpoint(retrofitBuilder: Retrofit.Builder,
                             okHttpClientBuilder: OkHttpClient.Builder,
                             @Named("placeEndpoint") baseUrl: String): Retrofit {
        return retrofitBuilder
                .client(okHttpClientBuilder.build())
                .baseUrl(baseUrl)
                .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Singleton
    @Provides
    fun providePlaceApiService(@Named("placeRetrofit") retrofit: Retrofit): PlaceApiService {
        return retrofit.create(PlaceApiService::class.java)
    }

}