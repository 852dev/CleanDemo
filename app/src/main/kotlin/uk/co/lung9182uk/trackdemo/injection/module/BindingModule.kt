package uk.co.lung9182uk.trackdemo.injection.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import uk.co.lung9182uk.trackdemo.injection.scope.PerActivity
import uk.co.lung9182uk.trackdemo.presentation.map.MapActivity
import uk.co.lung9182uk.trackdemo.presentation.place.PlaceActivity

@Module
abstract class BindingModule {

    @PerActivity
    @ContributesAndroidInjector
    abstract fun mapActivity(): MapActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun placeActivity(): PlaceActivity

}