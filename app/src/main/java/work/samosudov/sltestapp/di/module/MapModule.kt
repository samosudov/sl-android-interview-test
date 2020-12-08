package work.samosudov.sltestapp.di.module

import dagger.Binds
import dagger.Module
import work.samosudov.sltestapp.data.datasource.LocalSource
import work.samosudov.sltestapp.domain.repository.LocalRepository

@Module
abstract class MapModule {

    @Binds
    abstract fun provideLocalSource(localSource: LocalSource): LocalSource

    @Binds
    abstract fun provideLocalRepository(localRepository: LocalRepository): LocalRepository

}
