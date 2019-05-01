package ml.lylin.utils.fueldata

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import ml.lylin.utils.fueldata.db2.AppDB
import ml.lylin.utils.fueldata.db2.FillingRecord
import ml.lylin.utils.fueldata.db2.FillingRecordsDAO

class AppRepository(
        application: Application
) {

    private val appDB: AppDB = AppDB.getDatabase(application)
    private val fillingRecordsDAO = appDB.fillingRecordsDAO()
    val fillingRecordList: LiveData<List<FillingRecord>> = fillingRecordsDAO.getAll()

    @WorkerThread
    suspend fun insert(fillingRecord: FillingRecord) {
        fillingRecordsDAO.insert(fillingRecord)
    }

}