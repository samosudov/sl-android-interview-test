package work.samosudov.sltestapp.di.module

import dagger.Module
import work.samosudov.sltestapp.di.subcomponent.ServiceComponent
import work.samosudov.sltestapp.di.subcomponent.MapComponent

@Module(
    subcomponents = [
        MapComponent::class,
        ServiceComponent::class
    ]
)
class SubcomponentsModule
