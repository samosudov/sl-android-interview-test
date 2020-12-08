package work.samosudov.sltestapp.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "coordinates")
data class CoordinateRoom(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "row_id") val id: Int = 0,
        @ColumnInfo(name = "lat") var lat: Double,
        @ColumnInfo(name = "lng") var lng: Double,
        @ColumnInfo(name = "time") var time: Long
)