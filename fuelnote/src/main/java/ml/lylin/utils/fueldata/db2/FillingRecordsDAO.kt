package ml.lylin.utils.fueldata.db2

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * интерфейс взаимодействия с базой данных
 */
@Dao
interface FillingRecordsDAO {

    @Query("SELECT * FROM filling_records")
    fun getAll(): LiveData<List<FillingRecord>>

    @Insert
    fun insert(fillingRecord: FillingRecord)

    @Update
    fun update(fillingRecord: FillingRecord)

    @Delete
    fun delete(fillingRecord: FillingRecord)

}