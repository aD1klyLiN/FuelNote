package ml.lylin.utils.fueldata

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ml.lylin.utils.fueldata.db.AppDB
import ml.lylin.utils.fueldata.db.FillingRecord
import ml.lylin.utils.fueldata.db.FillingRecordsDAO

class AppRepository private constructor(
        context: Context
) {

    private val db = AppDB.getDatabase(context)

    val fillingRecordList: LiveData<List<FillingRecord>> = db.fillingRecordsDAO().getAll()

    @WorkerThread
    suspend fun getAll(): LiveData<List<FillingRecord>> {
        return db.fillingRecordsDAO().getAll()
    }

    @WorkerThread
    suspend fun insert(fillingRecord: FillingRecord) {
        db.fillingRecordsDAO().insert(fillingRecord)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: AppRepository? = null

        fun getRepository(context: Context): AppRepository {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = AppRepository(context)
                INSTANCE = instance
                return instance
            }
        }

    }

}