package uk.co.lung9182uk.trackdemo.presentation.base

import io.reactivex.disposables.CompositeDisposable
import uk.co.lung9182uk.trackdemo.presentation.base.mvp.BaseMVPPresenter

open class BasePresenter<V : BaseContract.View> : BaseMVPPresenter<V>(), BaseContract.Presenter<V> {

    val disposables: CompositeDisposable = CompositeDisposable()

    override fun onPresenterDestroy() = clear()

    private fun clear() {
        disposables.clear()
    }
}