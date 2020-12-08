package work.samosudov.sltestapp.data.datasource

import io.reactivex.Observable
import work.samosudov.sltestapp.data.model.room.CoordinateRoom


interface LocalSource {
    fun initCoordinates()
    fun fillCoordinates()
    fun getCoordinates() : Observable<List<CoordinateRoom>>
}
