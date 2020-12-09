package work.samosudov.sltestapp.feature.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.google.android.gms.maps.model.LatLng
import work.samosudov.sltestapp.R
import work.samosudov.sltestapp.SlTestApplication
import work.samosudov.sltestapp.feature.map.MapViewModel
import work.samosudov.sltestapp.feature.map.MapsActivity
import javax.inject.Inject

class CoordinateService : LifecycleService() {

    @Inject
    lateinit var mapViewModel: MapViewModel

    private val notificationManager: NotificationManager by lazy {
        this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()
        (application as SlTestApplication).appComponent.serviceComponent().create().inject(this)

        buildNotification()
        subscribeOn()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.extras?.get(INTENT_EXTRA_ID) == SERVICE_STOP_SELF) {
            stopSelf()
        } else {
            startForegroundService()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun subscribeOn() {
        mapViewModel.serviceCoordinateResult.observe(this) {
            updateNotification(it)
        }
    }

    private fun startForegroundService() {
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel =
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                IMPORTANCE_DEFAULT
            )

        notificationManager.createNotificationChannel(channel)
    }

    private fun updateNotification(latLng: LatLng) {
        notificationBuilder.setContentText("${latLng.latitude} ${latLng.longitude}")
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun buildNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val openActivityPendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MapsActivity::class.java).apply {
                this.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val stopIntent = Intent(this, CoordinateService::class.java)
        stopIntent.putExtra(INTENT_EXTRA_ID, SERVICE_STOP_SELF)
        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        notificationBuilder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Coordinates")
            .setContentText("...")
            .setContentIntent(openActivityPendingIntent)
            .addAction(R.mipmap.ic_launcher, "Stop", stopPendingIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationManager.cancel(NOTIFICATION_ID)
    }

    companion object {
        private const val NOTIFICATION_ID = 0
        private const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
        private const val NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME"

        private const val INTENT_EXTRA_ID = "INTENT_EXTRA_ID"
        private const val SERVICE_STOP_SELF = 0
    }
}