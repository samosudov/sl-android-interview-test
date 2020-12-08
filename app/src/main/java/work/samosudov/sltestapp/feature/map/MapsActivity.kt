package work.samosudov.sltestapp.feature.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import work.samosudov.sltestapp.R
import work.samosudov.sltestapp.SlTestApplication
import javax.inject.Inject


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var mapViewModel: MapViewModel

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as SlTestApplication).appComponent.mapComponent().create().inject(this)

        initView()
        subscribeUi()
    }

    override fun onStart() {
        super.onStart()
        //TODO:: stop service if it started
    }

    private fun initView() {
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun subscribeUi() {
        mapViewModel.coordinateResult.observe(this, {
            googleMap.addMarker(MarkerOptions().position(it))
            println("MapsActivity coordinateResult=$it")
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(37.421, -122.074)
        this.googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))
    }

    override fun onStop() {
        super.onStop()
        mapViewModel.switchStream(false)
        //TODO: 1. start service;
    }
}