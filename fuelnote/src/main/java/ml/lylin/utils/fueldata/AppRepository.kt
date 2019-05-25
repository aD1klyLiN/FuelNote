package ml.lylin.utils.fueldata

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ml.lylin.utils.fueldata.db.AppDB
import ml.lylin.utils.fueldata.db.FillingRecord

//репозиторий приложения, синглетон, содержит внутри себя Room-хелпер БД
class AppRepository private constructor(
        context: Context
) {

    //ссылка на Room-базу
    private val appDB = AppDB.getDatabase(context)

    //префы приложения
    private val preferences: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    //поле - размер списка записей
    private val listCount = preferences.getInt("list_count", 10)

    //поле - список всех записей, получен из базы
    val fillingRecordList: LiveData<List<FillingRecord>> = appDB.fillingRecordsDAO().getAll()

    /**функция возвращает последние [listCount] записей
     *
     */
    fun getList(): LiveData<List<FillingRecord>> {
        val list = fillingRecordList
        if (list.value == null) return list
        return if (list.value!!.size < listCount) {
            list
        } else {
            val newList = MutableLiveData<List<FillingRecord>>()
            newList.postValue(list.value!!.subList(0, listCount-1))
            newList
        }
    }

    /**
     * функция вставляет запись в базу
     */
    @WorkerThread
    fun insert(fillingRecord: FillingRecord) {
        appDB.fillingRecordsDAO().insert(fillingRecord)
    }

    /**
     * функция удаляет запись из базы
     */
    @WorkerThread
    fun delete(fillingRecord: FillingRecord) {
        appDB.fillingRecordsDAO().delete(fillingRecord)
    }

    /**
     * статическая часть обеспечивает единичность репозитория
     */
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