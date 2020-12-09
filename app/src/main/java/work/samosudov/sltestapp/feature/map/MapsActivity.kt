package work.samosudov.sltestapp.feature.map

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import work.samosudov.sltestapp.R
import work.samosudov.sltestapp.SlTestApplication
import work.samosudov.sltestapp.feature.service.CoordinateService
import javax.inject.Inject


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var mapViewModel: MapViewModel

    private lateinit var googleMap: GoogleMap
    private lateinit var markerPosition: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as SlTestApplication).appComponent.mapComponent().create().inject(this)

        initView()
        subscribeUi()
    }

    override fun onStart() {
        super.onStart()
        stopService(Intent(this, CoordinateService::class.java))
    }

    private fun initView() {
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun subscribeUi() {
        mapViewModel.coordinateResult.observe(this, {
            if (::markerPosition.isInitialized) {
                markerPosition.position = it
            } else {
                markerPosition = googleMap.addMarker(MarkerOptions().position(it))
            }

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
        startService(Intent(this, CoordinateService::class.java))
    }
}