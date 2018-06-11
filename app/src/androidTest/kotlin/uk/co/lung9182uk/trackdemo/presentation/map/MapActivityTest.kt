package uk.co.lung9182uk.trackdemo.presentation.map

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.Single
import org.hamcrest.Matchers.allOf

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.lung9182uk.trackdemo.TestApp
import uk.co.lung9182uk.trackdemo.domain.model.Place

@RunWith(AndroidJUnit4::class)
class MapActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<MapActivity>(MapActivity::class.java, false, false)

    private val VALID_NO_NETWORK_MESSAGE = "No network connected!"

    @Test
    fun verifyNoNetworkErrorMessage() {
        stubIsNetworkConnected(Single.just(false))
        stubGetLastSearch(Maybe.empty())
        activity.launchActivity(null)
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(VALID_NO_NETWORK_MESSAGE)))
                .check(matches(isDisplayed()))
    }

    private fun stubGetLastSearch(observable: Maybe<Place>) {
        whenever(TestApp.appComponent().placeRepository().getLastQuery())
                .thenReturn(observable)
    }

    private fun stubIsNetworkConnected(observable: Single<Boolean>) {
        whenever(TestApp.appComponent().placeRepository().isNetworkConnected())
                .thenReturn(observable)
    }

}