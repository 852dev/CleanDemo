package uk.co.lung9182uk.trackdemo.injection

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import uk.co.lung9182uk.trackdemo.TestApp
import uk.co.lung9182uk.trackdemo.domain.repository.PlaceRepository
import uk.co.lung9182uk.trackdemo.injection.component.AppComponent
import uk.co.lung9182uk.trackdemo.injection.module.BindingModule
import uk.co.lung9182uk.trackdemo.injection.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [TestAppModule::class, NetworkModule::class, BindingModule::class, AndroidSupportInjectionModule::class])
interface TestAppComponent : AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): TestAppComponent
    }
    fun inject(app: TestApp)
    fun placeRepository(): PlaceRepository

}