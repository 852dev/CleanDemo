package uk.co.lung9182uk.trackdemo.presentation.base.mvp

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver

abstract class BaseMVPPresenter<V : BaseMVPContract.View> : LifecycleObserver, BaseMVPContract.Presenter<V> {

    internal var view: V? = null
        private set

    val isViewAttached: Boolean
        get() = view != null

    override fun attachLifecycle(lifecycle: Lifecycle) = lifecycle.addObserver(this)

    override fun detachLifecycle(lifecycle: Lifecycle) = lifecycle.removeObserver(this)

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onPresenterCreate() {}

    override fun getView(): V? = view
}