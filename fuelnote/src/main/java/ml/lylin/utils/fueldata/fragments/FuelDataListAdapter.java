package ml.lylin.utils.fueldata.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import ml.lylin.utils.fueldata.R;
import ml.lylin.utils.fueldata.db.FuelData;
import ml.lylin.utils.fueldata.viewmodel.FuelDataViewModel;

public class FuelDataListAdapter extends RecyclerView.Adapter<FuelDataListAdapter.FuelDataListViewHolder> {

    private List<FuelData> fuelDataList;
    private LayoutInflater mInflater;
    private FuelDataViewModel mViewModel;


    public FuelDataListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mViewModel = ViewModelProviders.of((FragmentActivity) context).get(FuelDataViewModel.class);
        this.fuelDataList = mViewModel.getFuelDataList().getValue();
    }

    @NonNull
    @Override
    public FuelDataListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_list_item, parent, false);
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

    class FuelDataListViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        private TextView tvDate, tvMileage, tvFuelVolume;
        private final FuelDataListAdapter mAdapter;

        FuelDataListViewHolder(View itemView, FuelDataListAdapter adapter) {
            super(itemView);
            this.mAdapter = adapter;
            itemView.setOnLongClickListener(this);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMileage = itemView.findViewById(R.id.tvMileage);
            tvFuelVolume = itemView.findViewById(R.id.tvFuelVolume);
        }

        void bindData(FuelData fuelData) {
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

        @Override
        public boolean onLongClick(View v) {
            int position = getLayoutPosition();
            FuelData element = fuelDataList.get(position);
            mViewModel.deleteFuelData(element);
            mAdapter.notifyDataSetChanged();
            return true;
        }
    }

}
