package ml.lylin.utils.fueldata.db2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FuelData::class], version = 1)
abstract class FuelDataRoomDatabase : RoomDatabase() {

    abstract fun fuelDataDAO(): FuelDataDAO

    companion object {
        @Volatile
        private var INSTANCE: FuelDataRoomDatabase? = null

        fun getDatabase(context: Context): FuelDataRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room. databaseBuilder(
                        context.applicationContext,
                        FuelDataRoomDatabase::class.java,
                        "fueldata_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}