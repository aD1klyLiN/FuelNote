package ml.lylin.utils.fueldata.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import java.io.FileNotFoundException;
import java.util.List;

import ml.lylin.utils.fueldata.FuelDataRepository;
import ml.lylin.utils.fueldata.db.FuelData;

public class FuelDataViewModel extends AndroidViewModel {

    private FuelDataRepository mRepository;

    public FuelDataViewModel(@NonNull Application application) {
        super(application);
        mRepository = FuelDataRepository.getRepository(application);
    }

    public void insertFuelData (FuelData fuelData) {
        mRepository.insertFuelData(fuelData);
    }

    public LiveData<List<FuelData>> getFuelDataList() {
        return mRepository.getFuelDataList();
    }

    public void getFuelDataFromFile() {
        mRepository.getFuelDataListFromFile(getApplication());
    }

    public void backupFuelDataToFile() {
        mRepository.backupFuelDataToFile(getApplication());
    }

    public void deleteFuelData(FuelData fuelData) {
        mRepository.deleteFuelData(fuelData);
    }
}
