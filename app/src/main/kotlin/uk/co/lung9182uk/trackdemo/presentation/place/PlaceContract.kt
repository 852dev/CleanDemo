package uk.co.lung9182uk.trackdemo.presentation.place

import uk.co.lung9182uk.trackdemo.domain.model.Place
import uk.co.lung9182uk.trackdemo.presentation.base.BaseContract

interface PlaceContract {
    interface View : BaseContract.View {
        fun showPlace(place: Place)
    }

    interface Presenter : BaseContract.Presenter<PlaceContract.View>
}

