package ml.lylin.utils.fueldata

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import ml.lylin.utils.fueldata.db2.FuelData
import ml.lylin.utils.fueldata.db2.FuelDataDAO

class FuelDataRepo(private val fuelDataDAO: FuelDataDAO) {

    val fuelDataList: LiveData<List<FuelData>> = fuelDataDAO.getAll()

    @WorkerThread
    suspend fun insert(fuelData: FuelData) {
        fuelDataDAO.insert(fuelData)
    }

}