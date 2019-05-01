package ml.lylin.utils.fueldata.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ml.lylin.utils.fueldata.AppRepository
import ml.lylin.utils.fueldata.db2.FillingRecord
import ml.lylin.utils.fueldata.db2.AppDB
import kotlin.coroutines.CoroutineContext

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repo: AppRepository
    private val fillingRecordList: LiveData<List<FillingRecord>>
    private lateinit var fillingRecord: FillingRecord

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    init {
        val fuelDataDAO = AppDB.getDatabase(application).fillingRecordsDAO()
        repo = AppRepository(application)
        fillingRecordList = repo.fillingRecordList
    }

    fun insert(fillingRecord: FillingRecord) = scope.launch(Dispatchers.IO) {
        repo.insert(fillingRecord)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}