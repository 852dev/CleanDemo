package uk.co.lung9182uk.trackdemo.presentation.base

import android.os.Bundle
import android.support.annotation.CallSuper
import dagger.android.AndroidInjection
import uk.co.lung9182uk.trackdemo.presentation.base.mvp.BaseMVPActivity

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>> : BaseMVPActivity<V, P>() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
    
}