package ml.lylin.utils.fueldata.db2

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface FuelDataDAO {

    @Query("SELECT * FROM fuel_data")
    fun getAll(): LiveData<List<FuelData>>

    @Insert
    fun insert(fuelData: FuelData)

    @Update
    fun update(fuelData: FuelData)

    @Delete
    fun delete(fuelData: FuelData)

}