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

    //два моих поля для пробега и количества по умолчанию
    private val ZERO_MILEAGE = 0
    private val ZERO_VOLUME = 0

    //поля корутин Котлина, написаны согласно кодолабе
    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    //ссылка на репозиторий приложения
    private val repository: AppRepository = AppRepository.getRepository(application.applicationContext)

    // список с записями, читается из базы
    //TODO: сделать ограничение по количеству элементов
    val recordList= repository.fillingRecordList

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

    //вставка новой записи в базу
    fun insert() {
        //пустую запись вставлять не нужно
        if (record.value == null) return else {
            if (record.value!!.mileage == 0 || record.value!!.fuelVolume == 0) return
        }

        //вставка через корутину
        scope.launch(Dispatchers.IO) {
            record.value!!.apply {
                repository.insert(this.record)
            }
        }
    }

    //удаление записи из базы
    fun delete(fillingRecord: FillingRecord) {
        scope.launch(Dispatchers.IO) {
            repository.delete(fillingRecord)
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}