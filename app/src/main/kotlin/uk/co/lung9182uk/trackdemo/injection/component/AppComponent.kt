package uk.co.lung9182uk.trackdemo.injection.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import uk.co.lung9182uk.trackdemo.App
import uk.co.lung9182uk.trackdemo.injection.module.AppModule
import uk.co.lung9182uk.trackdemo.injection.module.BindingModule
import uk.co.lung9182uk.trackdemo.injection.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [BindingModule::class, AppModule::class, NetworkModule::class, AndroidSupportInjectionModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
}