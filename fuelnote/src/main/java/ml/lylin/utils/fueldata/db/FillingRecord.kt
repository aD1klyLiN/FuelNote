package ml.lylin.utils.fueldata.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * data-класс для записи о заправке
 */
@Entity(tableName = "filling_records")
data class FillingRecord (
        val day: Int, // день
        val month: Int, // месяц
        val year: Int, // год - дата заправки. Время знать не требуется (наверное)
        @PrimaryKey val mileage: Int, // пробег по счётчику, записывается вручную по одометру
        @ColumnInfo(name = "fuel_volume") val fuelVolume: Int // объём заправленного топлива
) {

    companion object {

        /**
         * статические методы для JSON-сериализации / десериализации
         */
        fun toJson(fillingRecord: FillingRecord): String {
            return GsonBuilder().create().toJson(fillingRecord)
        }

        fun fromJson(jsonFillingRecord: String): FillingRecord {
            return Gson().fromJson(jsonFillingRecord, FillingRecord::class.java)
        }

    }

}