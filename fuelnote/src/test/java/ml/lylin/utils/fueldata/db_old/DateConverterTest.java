package ml.lylin.utils.fueldata.db_old;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateConverterTest {

    private Date date;
    private long l;
    private DateConverter dc;

    @Before
    public void before() {
        date = Calendar.getInstance().getTime();
        l = date.getTime();
        dc = new DateConverter();
    }

    @Test
    public void dateToLong() {

        assertEquals(dc.dateToLong(date), l);

    }

    @Test
    public void longToDate() {

        assertEquals(dc.longToDate(l), date);
    }
}