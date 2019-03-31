package ml.lylin.utils.fueldata.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;

/**
 * класс-сущность (Entity) для Room
 * внутри поля таблицы
 */
@Entity(tableName = "fuel_data", indices = {@Index(value = "mileage", unique = true)})
public class FuelData {

    /**
     * поле даты
     */
    @NonNull
    private Calendar date;

    /**
     * поле пробега
     */
    @NonNull
    @PrimaryKey
    private int mileage;

    /**
     * поле для количества топлива
     */
    @NonNull
    @ColumnInfo(name = "fuel_volume")
    private int fuelVolume;

    public FuelData(@NonNull Calendar date, int mileage, int fuelVolume) {
        this.date = date;
        this.mileage = mileage;
        this.fuelVolume = fuelVolume;
    }

    @NonNull
    public Calendar getDate() {
        return date;
    }

    @NonNull
    public int getMileage() {
        return mileage;
    }

    @NonNull
    public int getFuelVolume() {
        return fuelVolume;
    }

    @Ignore
    public int getYear() {
        return date.get(Calendar.YEAR);
    }

    @Ignore
    public int getMonth() {
        return date.get(Calendar.MONTH);
    }

    @Ignore
    public int getDayOfMonth() {
        return date.get(Calendar.DAY_OF_MONTH);
    }

    @Ignore
    public String toJson() {
        return new GsonBuilder().create().toJson(this);
    }

    @Ignore
    public static FuelData fromJson(String source) {
        return new Gson().fromJson(source, FuelData.class);
    }

    @Override
    public boolean equals(Object obj) {
        // TODO: 16.09.18 метод не работает, переделать
        if (obj instanceof FuelData) {
            FuelData o = (FuelData) obj;
            return o.date.getTimeInMillis() == this.date.getTimeInMillis() && o.mileage == this.mileage && o.fuelVolume == this.fuelVolume;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash = hash + date.get(Calendar.DAY_OF_MONTH)*1000 + date.get(Calendar.MONTH)*100 + date.get(Calendar.YEAR)*10;
        hash = hash + mileage;
        hash = hash + fuelVolume;
        return hash;
    }
}
