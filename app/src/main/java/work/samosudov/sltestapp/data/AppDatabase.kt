package work.samosudov.sltestapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import work.samosudov.sltestapp.data.dao.CoordinateDao
import work.samosudov.sltestapp.data.model.room.CoordinateRoom

@Database(
    entities = [
        CoordinateRoom::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coordinateDao(): CoordinateDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "coordinates-db")
                .build()
        }

    }
}
