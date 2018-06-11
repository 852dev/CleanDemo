package uk.co.lung9182uk.trackdemo

import android.app.Activity
import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import android.support.multidex.MultiDex
import uk.co.lung9182uk.trackdemo.injection.component.DaggerAppComponent

class App : Application(), HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
        Stetho.initializeWithDefaults(this)
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}