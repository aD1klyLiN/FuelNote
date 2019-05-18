package ml.lylin.utils.fueldata.db_old;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class DateConverter {

    @TypeConverter
    public long dateToLong (Calendar date) {
        return date.getTimeInMillis();
    }

    @TypeConverter
    public Calendar longToDate (long l) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        return c;
    }
}
