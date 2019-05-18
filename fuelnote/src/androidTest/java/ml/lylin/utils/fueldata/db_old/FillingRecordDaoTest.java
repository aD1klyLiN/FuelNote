package ml.lylin.utils.fueldata.db_old;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.annotation.Nullable;
import androidx.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FillingRecordDaoTest {

    private FuelDataDB db;
    private FuelDataDao dao;

    @Before
    public void setUp() throws Exception {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                FuelDataDB.class)
                .build();
        dao = db.fuelDataDao();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void getAll() throws NullPointerException{
        //LiveData<List<FillingRecord>> listLiveData = TestHelper.getLiveDataList(1);
        List<FuelData> list = TestHelper.getList(1);

        dao.insert(list.get(0));
        LiveData<List<FuelData>> listLiveData = dao.getAll();
        listLiveData.observeForever(new Observer<List<FuelData>>() {
            @Override
            public void onChanged(@Nullable List<FuelData> fuelData) {
                List<FuelData> list1 = fuelData;
            }
        });
        List<FuelData> list1 = listLiveData.getValue();
        List<FuelData> list2 = dao.getList();
        //assertEquals(1, list1.size());
        assertEquals(1, list2.size());
        FuelData fuelData = list2.get(0);


        assertTrue(fuelData.equals(list.get(0)));
    }

    @Test
    public void getByMileage() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void updateFuelVolumeOfDate() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteAll() {
    }

    private static class TestHelper {
         static LiveData<List<FuelData>> getLiveDataList(int count) {
             MutableLiveData<List<FuelData>> listLiveData = new MutableLiveData<>();
             List<FuelData> list = getList(count);
             listLiveData.postValue(list);
             return listLiveData;
         }

         static List<FuelData> getList (int count) {
             List<FuelData> list = new ArrayList<>();
             for (int i = 0; i < count; i++) {
                 Calendar calendar = Calendar.getInstance();
                 int month = i;
                 if (i > 11) { month = i % 12; }
                 int day = i;
                 if (i > 28) { day = i % 28; }
                 calendar.set(2018,month,day);
                 Date date = calendar.getTime();
                 int mileage = 26589 + i*300;
                 int vol = 30;
                 list.add(new FuelData(calendar, mileage, vol));
             }
             return list;
         }
    }
}