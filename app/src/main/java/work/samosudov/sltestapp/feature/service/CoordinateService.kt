package work.samosudov.sltestapp.feature.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleRegistry
import work.samosudov.sltestapp.SlTestApplication
import work.samosudov.sltestapp.feature.map.MapViewModel
import javax.inject.Inject

class CoordinateService : Service() {

    @Inject
    lateinit var mapViewModel: MapViewModel

    override fun onCreate() {
        (application as SlTestApplication).appComponent.serviceComponent().create().inject(this)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {

    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}