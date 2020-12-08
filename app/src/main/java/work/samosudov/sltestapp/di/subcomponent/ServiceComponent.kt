package work.samosudov.sltestapp.di.subcomponent

import dagger.Subcomponent
import work.samosudov.sltestapp.di.ServiceScope
import work.samosudov.sltestapp.di.module.ServiceModule
import work.samosudov.sltestapp.feature.service.CoordinateService


@Subcomponent(
    modules = [ServiceModule::class]
)
@ServiceScope
interface ServiceComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ServiceComponent
    }

    fun inject(coordinateService: CoordinateService)

}
