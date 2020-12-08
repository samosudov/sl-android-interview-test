package work.samosudov.sltestapp.domain.repository

import io.reactivex.Observable
import work.samosudov.sltestapp.data.model.room.CoordinateRoom

interface LocalRepository {
    fun getCoordinate() : Observable<CoordinateRoom>
}
