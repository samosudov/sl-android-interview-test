package work.samosudov.sltestapp.data.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import work.samosudov.sltestapp.data.model.room.CoordinateRoom

@Dao
interface CoordinateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(coordinates: List<CoordinateRoom>) : Completable

    @Query("SELECT * FROM coordinates")
    fun getAll(): Observable<List<CoordinateRoom>>

    @Delete
    fun delete(address: CoordinateRoom)

    @Query("DELETE FROM coordinates")
    fun deleteAll() : Completable

}
