package uk.co.lung9182uk.trackdemo.presentation.base

import uk.co.lung9182uk.trackdemo.presentation.base.mvp.BaseMVPContract


interface BaseContract {

    interface View : BaseMVPContract.View {
        fun showLoading() {}
        fun hideLoading() {}
        fun showError(message: String) {}
    }

    interface Presenter<V : BaseMVPContract.View> : BaseMVPContract.Presenter<V>
}