package work.samosudov.sltestapp.data.datasource

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import work.samosudov.sltestapp.data.AppDatabase
import work.samosudov.sltestapp.data.model.room.CoordinateRoom
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DataBaseDataSource @Inject constructor(
    private val appDatabase: AppDatabase
) : LocalSource {

    override fun initCoordinates() {
        appDatabase
            .coordinateDao()
            .deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                fillCoordinates()
            }
    }

    override fun fillCoordinates() {
        appDatabase
            .coordinateDao()
            .insertAll(presetList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun getCoordinates() =
        appDatabase.coordinateDao().getAll()


    companion object {
        private val presetList by lazy {
            val count = 10
            var lat = 37.422
            var lng = -122.084
            var time = 1607447979L
            val list = mutableListOf<CoordinateRoom>()

            (0..count).forEach { _ ->
                list.add(CoordinateRoom(lat = lat, lng = lng, time = time))
                lat += 0.001f
                lng += 0.001f
                time += 5
            }

            list
        }
    }
}