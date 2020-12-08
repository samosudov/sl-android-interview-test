package work.samosudov.sltestapp.di.subcomponent

import dagger.Subcomponent
import work.samosudov.sltestapp.di.MapScope
import work.samosudov.sltestapp.di.module.MapModule
import work.samosudov.sltestapp.feature.map.MapsActivity


@Subcomponent(
    modules = [MapModule::class]
)
@MapScope
interface MapComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MapComponent
    }

    fun inject(mapsActivity: MapsActivity)

}
