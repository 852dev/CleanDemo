package uk.co.lung9182uk.trackdemo.presentation.base.mvp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity

abstract class BaseMVPActivity<V : BaseMVPContract.View, P : BaseMVPContract.Presenter<V>> : AppCompatActivity(), BaseMVPContract.View {

    lateinit var presenter: P

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java) as BaseViewModel<V, P>
        var isPresenterCreated = false
        if (viewModel.presenter == null) {
            viewModel.presenter = initPresenter()
            isPresenterCreated = true
        }
        presenter = viewModel.presenter as P
        presenter.attachLifecycle(lifecycle)
        presenter.attachView(this as V)
        if (isPresenterCreated)
            presenter.onPresenterCreate()
    }

    @CallSuper
    override fun onDestroy() {
        presenter.detachLifecycle(lifecycle)
        presenter.detachView()
        super.onDestroy()
        presenter.onPresenterDestroy()
    }

    protected abstract fun initPresenter(): P


}