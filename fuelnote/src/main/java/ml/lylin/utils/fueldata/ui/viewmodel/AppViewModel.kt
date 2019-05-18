package ml.lylin.utils.fueldata.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ml.lylin.utils.fueldata.AppRepository
import ml.lylin.utils.fueldata.db.FillingRecord
import ml.lylin.utils.fueldata.db.MutableFillingRecord
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class AppViewModel(
        application: Application
): AndroidViewModel(application) {

    private val ZERO_MILEAGE = 0
    private val ZERO_VOLUME = 0

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    private val repository: AppRepository = AppRepository.getRepository(application.applicationContext)

    // список с записями, читается из базы
    //TODO: сделать ограничение по количеству элементов
    val recordList= MutableLiveData<ArrayList<FillingRecord>>().apply {
        this.postValue(arrayListOf())
    }

    // лайвдата для новой записи
    val record = MutableLiveData<MutableFillingRecord>().apply {
        val calendar: Calendar = Calendar.getInstance()
        this.value = MutableFillingRecord(
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR),
                ZERO_MILEAGE,
                ZERO_VOLUME
        )
    }

    fun insert() {

        if (record.value == null) return else {
            if (record.value!!.mileage == 0 || record.value!!.fuelVolume == 0) return
        }

        scope.launch(Dispatchers.IO) {
            record.value!!.apply {
                repository.insert(this.record)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}