package work.samosudov.sltestapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import work.samosudov.sltestapp.di.module.LocalStorageModule
import work.samosudov.sltestapp.di.module.SubcomponentsModule
import work.samosudov.sltestapp.di.subcomponent.MapComponent
import work.samosudov.sltestapp.di.subcomponent.ServiceComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [LocalStorageModule::class, SubcomponentsModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun mapComponent(): MapComponent.Factory
    fun serviceComponent(): ServiceComponent.Factory

}
