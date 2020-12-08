package work.samosudov.sltestapp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import work.samosudov.sltestapp.data.AppDatabase
import work.samosudov.sltestapp.data.datasource.DataBaseDataSource
import work.samosudov.sltestapp.data.repository.DatabaseRepository

@Module
class LocalStorageModule {

    @Provides
    fun provideLocalRepository(dataBaseDataSource: DataBaseDataSource) = DatabaseRepository(dataBaseDataSource)

    @Provides
    fun provideRoomDatabase(context: Context) = AppDatabase.getInstance(context)

    @Provides
    fun provideLocalSource(appDatabase: AppDatabase) = DataBaseDataSource(appDatabase)

}
