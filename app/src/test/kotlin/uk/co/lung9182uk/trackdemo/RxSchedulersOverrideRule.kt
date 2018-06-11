package uk.co.lung9182uk.trackdemo

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Callable

class RxSchedulersOverrideRule : TestRule {

    private val rxAndroidSchedulersHook = Function<Callable<Scheduler>, Scheduler> { Schedulers.trampoline() }

    private val rxJavaImmediateScheduler = Function<Scheduler, Scheduler> { Schedulers.trampoline() }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {

            @Throws(Throwable::class)
            override fun evaluate() {

                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(rxAndroidSchedulersHook)

                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler(rxJavaImmediateScheduler)
                RxJavaPlugins.setNewThreadSchedulerHandler(rxJavaImmediateScheduler)

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}