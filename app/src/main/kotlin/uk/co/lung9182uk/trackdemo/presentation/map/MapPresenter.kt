package uk.co.lung9182uk.trackdemo.presentation.map


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import uk.co.lung9182uk.trackdemo.domain.interactor.MapInteractor
import uk.co.lung9182uk.trackdemo.domain.model.Place
import uk.co.lung9182uk.trackdemo.domain.model.Prediction
import uk.co.lung9182uk.trackdemo.presentation.base.BasePresenter
import javax.inject.Inject

class MapPresenter @Inject constructor(private val mapInteractor: MapInteractor) : BasePresenter<MapContract.View>(), MapContract.Presenter {

    private var predictions = emptyList<Prediction>()

    private lateinit var place: Place

    override fun checkNetwork() {
        disposables.add(mapInteractor.checkNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { connected: Boolean ->
                            if (!connected) {
                                view?.showError("No network connected!")
                                getLastQuery()
                            }
                        },
                        onError = { exception: Throwable ->
                            onRequestError(exception.message + "")

                        }
                )
        )
    }

    override fun getPlace(name: String) {
        view?.showLoading()
        val placeId = predictions.find {
            it.description == name
        }?.placeId

        disposables.add(mapInteractor.getPlaceDetail("" + placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { t: Place ->
                            view?.hideLoading()
                            view?.showPlaceOnMap(t)
                            place = t
                        },
                        onError = { exception: Throwable ->
                            onRequestError(exception.message + "")

                        }
                )
        )

    }

    override fun getAutoComplete(input: String) {

        disposables.add(mapInteractor.getAutoComplete(input)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { t: List<Prediction> ->
                            predictions = t
                            view?.showAutoComplete(predictions.map { it.description })
                        },
                        onError = { exception: Throwable ->
                            onRequestError(exception.message + "")

                        }
                )
        )

    }

    override fun getNearby(location: String) {

        view?.showLoading()

        disposables.add(mapInteractor.searchNearby(location, "1000", "pub")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { t: List<Place> ->
                            view?.hideLoading()
                            view?.showNearby(t)
                        },
                        onError = { exception: Throwable ->
                            onRequestError(exception.message + "")

                        }
                )
        )

    }

    override fun getLastQuery() {
        disposables.add(mapInteractor.getLastSearchPlace()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { t: Place ->
                            view?.showLastQuery(t)
                        },
                        onError = { exception: Throwable ->
                            onRequestError(exception.message + "")
                        }
                )
        )
    }

    private fun onRequestError(errorMessage: String) {
        view?.run {
            hideLoading()
            showError(errorMessage)
        }
    }


}