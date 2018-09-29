package ml.lylin.utils.fueldata.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Database(entities = {FuelData.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class FuelDataDB extends RoomDatabase {

    public abstract FuelDataDao fuelDataDao();

    private static FuelDataDB INSTANCE;

    public static FuelDataDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FuelDataDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            FuelDataDB.class,
                            "word_database")
                            .addCallback(mCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback mCallback = new Callback() {
        @Override
        public void onCreate(@NonNull final SupportSQLiteDatabase db) {
            super.onCreate(db);

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // TODO: 16.09.18 это временно, переделать
            Executors.newSingleThreadExecutor().execute(new dbRunnable(INSTANCE));
        }
    };

    private static class dbRunnable implements Runnable {

        private FuelDataDao mDao;

        dbRunnable(FuelDataDB fuelDataDB) {
            this.mDao = fuelDataDB.fuelDataDao();
        }

        @Override
        public void run() {
            //mDao.deleteAll();
        }
    }
}
