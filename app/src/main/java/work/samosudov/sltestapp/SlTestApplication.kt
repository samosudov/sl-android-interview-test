package work.samosudov.sltestapp

import android.app.Application
import work.samosudov.sltestapp.di.ApplicationComponent
import work.samosudov.sltestapp.di.DaggerApplicationComponent


class SlTestApplication : Application() {

    val appComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent() : ApplicationComponent {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}
