package ml.lylin.utils.fueldata.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * DAO для Room
 */
@Dao
public interface FuelDataDao {

    // TODO: 16.09.18 разобраться в различиях getAll и getList

    @Query("SELECT * FROM fuel_data")
    LiveData<List<FuelData>> getAll();

    @Query("SELECT * FROM fuel_data")
    List<FuelData> getList();

    @Query("SELECT * FROM fuel_data WHERE mileage = :mileage")
    LiveData<FuelData> getByMileage(int mileage);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FuelData fuelData);

    @Query("UPDATE fuel_data SET fuel_volume = :newFuelVolume WHERE mileage = :mileage")
    void updateFuelVolumeOfDate(int mileage, int newFuelVolume);

    @Update
    void update(FuelData fuelData);

    @Delete
    void delete(FuelData fuelData);

    @Query("DELETE FROM fuel_data")
    void deleteAll();
}
