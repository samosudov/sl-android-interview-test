package work.samosudov.sltestapp.data.repository

import io.reactivex.Observable
import work.samosudov.sltestapp.data.datasource.DataBaseDataSource
import work.samosudov.sltestapp.domain.repository.LocalRepository
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val dataBaseDataSource: DataBaseDataSource
) : LocalRepository {

    init {
        initCoordinates()
    }

    override fun getCoordinate() =
        dataBaseDataSource
            .getCoordinates()
            .flatMap {
                Observable.fromIterable(it)
            }

    private fun initCoordinates() {
        dataBaseDataSource.initCoordinates()
    }

}