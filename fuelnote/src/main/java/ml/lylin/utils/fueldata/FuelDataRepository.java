package ml.lylin.utils.fueldata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.Executors;

import ml.lylin.utils.fueldata.db.FuelData;
import ml.lylin.utils.fueldata.db.FuelDataDB;
import ml.lylin.utils.fueldata.db.FuelDataDao;

public class FuelDataRepository {

    public static FuelDataRepository INSTANCE;

    private FuelDataDao fuelDataDao;
    private LiveData<List<FuelData>> fuelDataList;

    private FuelDataRepository(Context context) {
        FuelDataDB fuelDataDB = FuelDataDB.getDatabase(context);
        fuelDataDao = fuelDataDB.fuelDataDao();
        fuelDataList = fuelDataDao.getAll();
    }

    public static FuelDataRepository getRepository(Context context) {
        if (INSTANCE == null) {
            synchronized (FuelDataDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FuelDataRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<FuelData>> getFuelDataList() {
        // TODO: 16.09.18 переделать в асинхронный запрос
        List<FuelData> listFromDB = fuelDataList.getValue();
        MutableLiveData<List<FuelData>> listToUI = new MutableLiveData<>();
        int listEnd = 0;
        if (listFromDB != null) {
            listEnd = listFromDB.size();
        }
        int length = 10;
        listToUI.setValue(listFromDB);
        if (listEnd > length) {
            listToUI.setValue(listFromDB.subList(listEnd - 10, listEnd));
        }
        return fuelDataList;
    }

    public void getFuelDataListFromFile(Context context) {

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(context.openFileInput("backup")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try {
            String cache;
            while ((cache = bufferedReader.readLine()) != null) {
                new InsertAsyncTask(fuelDataDao).execute(FuelData.fromJson(cache));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backupFuelDataToFile(Context context) {
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(context.openFileOutput("backup", Context.MODE_PRIVATE)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        if (fuelDataList.getValue() != null) {
            try {
                for (FuelData fd:fuelDataList.getValue()) {
                    try {
                        bufferedWriter.write(fd.toJson());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }}
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertFuelData (FuelData fuelData) {
        // TODO: 16.09.18 переделать асинхронный запрос
        new InsertAsyncTask(fuelDataDao).execute(fuelData);

    }

    public void deleteFuelData(FuelData fuelData) {
        Executors.newSingleThreadExecutor().execute(new DeleteRun(fuelData));
    }

    private static class InsertAsyncTask extends AsyncTask<FuelData, Void, Void> {

        private FuelDataDao mDao;

        InsertAsyncTask(FuelDataDao mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(FuelData... fuelData) {
            mDao.insert(fuelData[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class DeleteRun implements Runnable {

        private FuelData fuelData;

        DeleteRun(FuelData fuelData) {
            this.fuelData = fuelData;
        }

        @Override
        public void run() {
            fuelDataDao.delete(fuelData);
        }
    }
}