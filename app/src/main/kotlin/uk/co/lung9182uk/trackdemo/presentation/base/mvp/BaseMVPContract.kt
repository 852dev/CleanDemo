package uk.co.lung9182uk.trackdemo.presentation.base.mvp

import android.arch.lifecycle.Lifecycle

interface BaseMVPContract {
    interface View

    interface Presenter<V : View> {
        fun attachView(view: V)
        fun detachView()
        fun getView(): V?
        fun onPresenterCreate()
        fun onPresenterDestroy()
        fun attachLifecycle(lifecycle: Lifecycle)
        fun detachLifecycle(lifecycle: Lifecycle)
    }
}