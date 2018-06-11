package uk.co.lung9182uk.trackdemo.presentation.base.mvp

import android.arch.lifecycle.ViewModel

class BaseViewModel<V : BaseMVPContract.View, P : BaseMVPContract.Presenter<V>> : ViewModel() {

    var presenter: P? = null

    override fun onCleared() {
        super.onCleared()
        presenter?.onPresenterDestroy()
        presenter = null
    }
}