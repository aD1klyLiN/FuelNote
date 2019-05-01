package ml.lylin.utils.fueldata.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ml.lylin.utils.fueldata.AppRepository
import ml.lylin.utils.fueldata.db2.FillingRecord
import java.util.Calendar
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.set

class AppViewModel(
        appRepository: AppRepository
): ViewModel() {

    private val ZERO_MILEAGE = 0
    private val ZERO_VOLUME = 0

    // список с записями, читается из базы
    //TODO: сделать ограничение по количеству элементов
    val recordList: LiveData<List<FillingRecord>> = appRepository.fillingRecordList

    // лайвдата для новой записи
    val record: MutableLiveData<FillingRecord> = MutableLiveData()

    // сюда собираем данные для новой записи
    val fragmentData = MutableLiveData<Map<String,Int?>>().apply {
        val calendar: Calendar = Calendar.getInstance()
        val dataMap = HashMap<String,Int>()
        dataMap["day"] = calendar.get(Calendar.DAY_OF_MONTH)
        dataMap["month"] = calendar.get(Calendar.MONTH)
        dataMap["day_of_week"] = calendar.get(Calendar.DAY_OF_WEEK)
        dataMap["mileage"] = ZERO_MILEAGE
        dataMap["fuel_volume"] = ZERO_VOLUME
        this.postValue(dataMap)
    }

}