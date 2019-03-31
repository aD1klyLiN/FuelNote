package ml.lylin.utils.fueldata.db2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 *
 */
@Entity(tableName = "fuel_data")
data class FuelData (
        val day: Int,
        val month: Int,
        val year: Int,
        @PrimaryKey val mileage: Int,
        @ColumnInfo(name = "fuel_volume") val fuelVolume: Int) {

    companion object {

        /**
         *
         */
        fun toJson(fuelData: FuelData): String {
            return GsonBuilder().create().toJson(fuelData)
        }

        fun fromJson(jsonFuelData: String): FuelData {
            return Gson().fromJson(jsonFuelData, FuelData::class.java)
        }

    }

}