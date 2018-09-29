package ml.lylin.utils.fueldata.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ml.lylin.utils.fueldata.R;
import ml.lylin.utils.fueldata.db.FuelData;

public class FuelDataListAdapter extends RecyclerView.Adapter<FuelDataListAdapter.FuelDataListViewHolder> {

    private final List<FuelData> fuelDataList;
    private LayoutInflater mInflater;
    private ListFragment.OnFragmentInteractionListener mListener;

    public FuelDataListAdapter(Context context, ListFragment.OnFragmentInteractionListener mListener) {
        mInflater = LayoutInflater.from(context);
        this.mListener = mListener;
        this.fuelDataList = mListener.getFuelDataList();
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

    class FuelDataListViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        private TextView tvDate, tvMileage, tvFuelVolume;
        private final FuelDataListAdapter mAdapter;

        FuelDataListViewHolder(View itemView, FuelDataListAdapter adapter) {
            super(itemView);
            this.mAdapter = adapter;
            itemView.setOnLongClickListener(this);
            //itemView.setOn
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMileage = itemView.findViewById(R.id.tvMileage);
            tvFuelVolume = itemView.findViewById(R.id.tvFuelVolume);
        }

        void bindData(FuelData fuelData) {
            String dateString = String.format(Locale.ENGLISH,
                    "%d/%d/%d",
                    fuelData.getDayOfMonth(),
                    fuelData.getMonth() + 1,
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
            mListener.deleteItem(element);
            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemRemoved(position);
            v.invalidate();
            v.refreshDrawableState();
            return true;
        }
    }

}
