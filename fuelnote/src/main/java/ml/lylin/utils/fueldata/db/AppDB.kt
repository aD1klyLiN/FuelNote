package ml.lylin.utils.fueldata.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * база данных приложения
 * пока что только одна таблица из [FillingRecord]-записей
 */
@Database(entities = [FillingRecord::class], version = 1)
abstract class AppDB : RoomDatabase() {

    abstract fun fillingRecordsDAO(): FillingRecordsDAO

    companion object {
        // объект базы - синглетон
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getDatabase(context: Context): AppDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context,
                        AppDB::class.java,
                        "app_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}