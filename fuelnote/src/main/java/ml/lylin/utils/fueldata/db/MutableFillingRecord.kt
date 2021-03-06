package ml.lylin.utils.fueldata.db

import java.util.*

class MutableFillingRecord(
        var day: Int,
        var month: Int,
        var year: Int,
        var mileage: Int,
        var fuelVolume: Int
) {

    val record
        get() = FillingRecord(day, month, year, mileage, fuelVolume)

    fun getDayOfWeek(): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val d = calendar.get(Calendar.DAY_OF_WEEK)
        return d
    }

}