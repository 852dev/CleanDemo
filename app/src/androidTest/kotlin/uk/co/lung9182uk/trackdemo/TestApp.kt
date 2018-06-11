package uk.co.lung9182uk.trackdemo

import android.app.Activity
import android.app.Application
import android.support.test.InstrumentationRegistry
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import uk.co.lung9182uk.trackdemo.injection.DaggerTestAppComponent
import uk.co.lung9182uk.trackdemo.injection.TestAppComponent
import javax.inject.Inject

open class TestApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    lateinit var appComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestAppComponent
                .builder()
                .application(this)
                .build()
        appComponent.inject(this)
    }

    companion object {
        fun appComponent(): TestAppComponent {
            return (InstrumentationRegistry.getTargetContext().applicationContext as TestApp).appComponent
        }
    }
    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}