package uk.co.lung9182uk.trackdemo.presentation.place

import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_place.*
import uk.co.lung9182uk.trackdemo.domain.model.Place
import uk.co.lung9182uk.trackdemo.presentation.base.BaseActivity
import uk.co.lung9182uk.trackdemo.util.L
import javax.inject.Inject

class PlaceActivity : BaseActivity<PlaceContract.View, PlaceContract.Presenter>(), PlaceContract.View {

    @Inject
    lateinit var placePresenter: PlacePresenter

    override fun initPresenter(): PlaceContract.Presenter = placePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(L.activity_place)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        showPlace(intent.getParcelableExtra("place"))
    }

    override fun showPlace(place: Place) {
        supportActionBar?.title = place.name
        nameView.text = "Name : ${place.name}"
        addressView.text = "Formatted Address : ${place.formattedAddress}"
        typeView.text = "Type : ${place.types?.joinToString()}"
        Glide.with(this)
                .load(place.icon)
                .into(iconView)
    }
}
