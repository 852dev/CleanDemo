package uk.co.lung9182uk.trackdemo.presentation.map

import uk.co.lung9182uk.trackdemo.domain.model.Place
import uk.co.lung9182uk.trackdemo.presentation.base.BaseContract

interface MapContract {
    interface View : BaseContract.View {
        fun showAutoComplete(predictions: List<String>)
        fun showPlaceOnMap(place: Place)
        fun showNearby(place: List<Place>)
        fun showLastQuery(place: Place)
    }

    interface Presenter : BaseContract.Presenter<MapContract.View> {
        fun getAutoComplete(input: String)
        fun getPlace(name: String)
        fun getNearby(location: String)
        fun getLastQuery()
        fun checkNetwork()
    }
}

