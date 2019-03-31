package ml.lylin.utils.fueldata.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ml.lylin.utils.fueldata.FuelDataRepo
import ml.lylin.utils.fueldata.db2.FuelData
import ml.lylin.utils.fueldata.db2.FuelDataRoomDatabase
import kotlin.coroutines.CoroutineContext

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repo: FuelDataRepo
    private val fuelDataList: LiveData<List<FuelData>>

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    init {
        val fuelDataDAO = FuelDataRoomDatabase.getDatabase(application).fuelDataDAO()
        repo = FuelDataRepo(fuelDataDAO)
        fuelDataList = repo.fuelDataList
    }

    fun insert(fuelData: FuelData) = scope.launch(Dispatchers.IO) {
        repo.insert(fuelData)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}