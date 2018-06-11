package uk.co.lung9182uk.trackdemo.presentation.map

import android.location.Location
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.SearchView
import android.view.Menu
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_map.*
import uk.co.lung9182uk.trackdemo.R
import uk.co.lung9182uk.trackdemo.presentation.base.BaseActivity
import javax.inject.Inject
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.support.v4.view.MenuItemCompat
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_bottom_sheet.*
import org.jetbrains.anko.startActivity
import uk.co.lung9182uk.trackdemo.domain.model.Place
import uk.co.lung9182uk.trackdemo.presentation.place.PlaceActivity
import uk.co.lung9182uk.trackdemo.presentation.widget.cell.TextCell
import uk.co.lung9182uk.trackdemo.util.L
import uk.co.lung9182uk.trackdemo.util.extension.hide
import uk.co.lung9182uk.trackdemo.util.extension.hideKeyboard
import uk.co.lung9182uk.trackdemo.util.extension.show


class MapActivity : BaseActivity<MapContract.View, MapContract.Presenter>(), MapContract.View, GoogleMap.OnMarkerClickListener {

    @Inject
    lateinit var mapPresenter: MapPresenter

    override fun initPresenter(): MapContract.Presenter = mapPresenter

    private lateinit var googleMap: GoogleMap

    private lateinit var adapter: ArrayAdapter<String>

    private lateinit var searchAutoComplete: SearchView.SearchAutoComplete

    private lateinit var searchView: SearchView

    private lateinit var searchMenu: MenuItem

    private var targetMarker: Marker? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private var nearbyMarkers: MutableList<Marker> = mutableListOf()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(L.activity_map)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync({ mMap ->
            googleMap = mMap
            googleMap.isMyLocationEnabled = true
            googleMap.setOnMarkerClickListener(this)
        })
        adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, emptyList())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location ->
            moveCamera(LatLng(location.latitude, location.longitude))
            mapPresenter.getNearby("${location?.latitude},${location?.longitude}")
        }
        bottomSheetBehavior = BottomSheetBehavior.from<View>(bottomSheetView)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search_menu, menu)
        searchMenu = menu.findItem(R.id.action_search)
        searchView = MenuItemCompat.getActionView(searchMenu) as SearchView

        searchAutoComplete = searchView.findViewById<View>(android.support.v7.appcompat.R.id.search_src_text) as SearchView.SearchAutoComplete
        searchAutoComplete.setAdapter(adapter)

        searchAutoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, itemIndex, id ->
            val queryString = adapterView.getItemAtPosition(itemIndex) as String
            searchAutoComplete.setText("" + queryString)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mapPresenter.getPlace("" + query)
                hideKeyboard()
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                mapPresenter.getAutoComplete(newText)
                return false
            }
        })

        mapPresenter.checkNetwork()
        return true
    }

    override fun showAutoComplete(predictions: List<String>) {
        adapter.clear()
        adapter.addAll(predictions)
        adapter.filter.filter(searchAutoComplete.text, null)
        adapter.notifyDataSetChanged()
    }

    override fun showPlaceOnMap(place: Place) {

        if (targetMarker != null) {
            targetMarker!!.remove()
        }

        var markerOption = MarkerOptions()
                .position(place.location)
                .title(place.name)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

        targetMarker = googleMap.addMarker(markerOption)
        targetMarker?.tag = place

        moveCamera(place.location)

    }

    override fun showLastQuery(place: Place) {
        searchMenu.expandActionView()
        searchView.setQuery("" + place.name, false)
        showPlaceOnMap(place)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker == targetMarker) {
            val place = marker?.tag as Place
            startActivity<PlaceActivity>("place" to place)
        }
        return false
    }

    override fun showNearby(place: List<Place>) {
        nearbyRecyclerView.removeAllCells()
        nearbyMarkers.forEach {
            it.remove()
        }

        nearbyMarkers = mutableListOf()

        val textCells = ArrayList<TextCell>()

        place.forEach {
            val cell = TextCell(Pair(it.name, "" + it.types?.joinToString()))
            textCells.add(cell)
            nearbyMarkers.add(googleMap.addMarker(MarkerOptions()
                    .position(it.location)
                    .title(it.name)))
        }

        nearbyRecyclerView.addCells(textCells)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun showLoading() {
        pb.show()
    }

    override fun hideLoading() {
        pb.hide()
    }

    override fun showError(message: String) {
        Snackbar.make(rootView, message!!, Snackbar.LENGTH_LONG).show()
    }

    private fun moveCamera(latLng: LatLng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.animateCamera(CameraUpdateFactory.zoomIn())
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f), 2000, null)
    }

}
