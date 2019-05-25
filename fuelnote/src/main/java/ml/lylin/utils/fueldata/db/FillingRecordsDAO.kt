package ml.lylin.utils.fueldata.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * интерфейс взаимодействия с базой данных
 */
@Dao
interface FillingRecordsDAO {

    @Query("SELECT * FROM filling_records ORDER BY year DESC, month DESC, day DESC")
    fun getAll(): LiveData<List<FillingRecord>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fillingRecord: FillingRecord)

    @Update
    fun update(fillingRecord: FillingRecord)

    @Delete
    fun delete(fillingRecord: FillingRecord)

}