package ml.lylin.utils.fueldata.db_old;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;
import androidx.annotation.NonNull;

import java.util.concurrent.Executors;

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
