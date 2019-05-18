package ml.lylin.utils.fueldata.fragments;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import ml.lylin.utils.fueldata.R;
import ml.lylin.utils.fueldata.db_old.FuelData;

public class FuelDataListAdapter extends RecyclerView.Adapter<FuelDataListAdapter.FuelDataListViewHolder> {

    private List<FuelData> fuelDataList;
    private ItemClickListener mListener;


    public FuelDataListAdapter(List<FuelData> fuelDataList, ItemClickListener listener) {
        this.fuelDataList = fuelDataList;
        mListener = listener;
    }

    @NonNull
    @Override
    public FuelDataListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);
        return new FuelDataListViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull final FuelDataListViewHolder holder, int position) {
        FuelData fuelData = fuelDataList.get(position);
        holder.bindData(fuelData);

    }

    @Override
    public int getItemCount() {

        if (fuelDataList != null) {
            return fuelDataList.size();
        } else {
            return 0;
        }

    }

    public void setFuelDataList(List<FuelData> fuelDataList) {
        this.fuelDataList = fuelDataList;
    }

    public List<FuelData> getFuelDataList() {
        return fuelDataList;
    }

    class FuelDataListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDate, tvMileage, tvFuelVolume;
        private final FuelDataListAdapter mAdapter;

        FuelDataListViewHolder(View itemView, FuelDataListAdapter adapter) {
            super(itemView);
            this.mAdapter = adapter;
            itemView.setOnLongClickListener(view -> mAdapter.mListener.onLongClick(this));
        }

        void bindData(FuelData fuelData) {
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMileage = itemView.findViewById(R.id.tvMileage);
            tvFuelVolume = itemView.findViewById(R.id.tvFuelVolume);
            String dateDay = fuelData.getDayOfMonth() > 9 ? Integer.toString(fuelData.getDayOfMonth()) : "0" + Integer.toString(fuelData.getDayOfMonth());
            String dateMonth = fuelData.getMonth() > 8 ? Integer.toString(fuelData.getMonth() + 1) : "0" + Integer.toString(fuelData.getMonth() + 1);
            String dateString = String.format(Locale.ENGLISH,
                    "%s/%s/%d",
                    dateDay,
                    dateMonth,
                    fuelData.getYear());
            tvDate.setText(dateString);
            String mileage = String.format(
                    Locale.ENGLISH,
                    "%d",
                    fuelData.getMileage());
            tvMileage.setText(mileage);
            String fuelVolume = String.format(
                    Locale.ENGLISH,
                    "%d",
                    fuelData.getFuelVolume());
            tvFuelVolume.setText(fuelVolume);
        }

    }

    interface ItemClickListener {
        boolean onLongClick(FuelDataListViewHolder viewHolder);
    }

}
