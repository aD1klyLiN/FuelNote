package ml.lylin.utils.fueldata.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

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
