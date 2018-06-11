package uk.co.lung9182uk.trackdemo.presentation.map

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import uk.co.lung9182uk.trackdemo.RxSchedulersOverrideRule
import uk.co.lung9182uk.trackdemo.domain.interactor.MapInteractor
import uk.co.lung9182uk.trackdemo.domain.model.Place
import uk.co.lung9182uk.trackdemo.place.PlaceFactory

@RunWith(JUnit4::class)
class MapPresenterTest {

    private lateinit var presenter: MapPresenter
    private lateinit var interactor: MapInteractor
    private lateinit var view: MapContract.View

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        interactor = mock()
        view = mock()
        presenter = MapPresenter(interactor)
        presenter.attachView(view)
    }

    @Test
    fun `verify no network have last search should show last search`() {
        stubIsNetworkConnected(Single.just(false))
        val lastPlace = PlaceFactory.makePlace()
        stubGetLastSearch(Maybe.just(lastPlace))
        presenter.checkNetwork()
        verify(view).showLastQuery(lastPlace)
    }

    @Test
    fun `verify no network no last search should not show last search`() {
        stubIsNetworkConnected(Single.just(false))
        val lastPlace = PlaceFactory.makePlace()
        stubGetLastSearch(Maybe.empty())
        presenter.checkNetwork()
        verify(view, Mockito.never()).showLastQuery(lastPlace)

    }

    private fun stubIsNetworkConnected(observable: Single<Boolean>) {
        whenever(interactor.checkNetwork())
                .thenReturn(observable)
    }

    private fun stubGetLastSearch(observable: Maybe<Place>) {
        whenever(interactor.getLastSearchPlace())
                .thenReturn(observable)
    }

    @After
    fun tearDown() {
        presenter.detachView()

    }

}