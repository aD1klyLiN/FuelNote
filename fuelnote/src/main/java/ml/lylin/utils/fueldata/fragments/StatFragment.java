package ml.lylin.utils.fueldata.fragments;

import android.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ml.lylin.utils.fueldata.R;
import ml.lylin.utils.fueldata.db.FuelData;
import ml.lylin.utils.fueldata.viewmodel.FuelDataViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatFragment extends Fragment {

    private int averageFlow = 0;
    private List<FuelData> fuelDataList;

    public StatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatFragment newInstance() {
        StatFragment fragment = new StatFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_stat, container, false);
        TextView tvAverageFlow = fragmentView.findViewById(R.id.averageFlowText);
        TextView tvPendingMileage = fragmentView.findViewById(R.id.pendingMileageText);
        tvAverageFlow.setText(calculateAverageFlow());
        tvPendingMileage.setText(calculatePendingMileage());
        return fragmentView;
    }

    private String calculateAverageFlow() {
        if (fuelDataList == null) throw new NullPointerException();
        int listSize = fuelDataList.size();
        for (int i = (listSize - 1); i > (listSize - 6); i--) {
            int mileageStart = fuelDataList.get(i-1).getMileage();
            int mileageEnd = fuelDataList.get(i).getMileage();
            int fuelVolume = fuelDataList.get(i-1).getFuelVolume();
            double flow = fuelVolume*10000/(mileageEnd - mileageStart);
            averageFlow = averageFlow + (int) (flow);
        }
        averageFlow = averageFlow/5;
        StringBuilder stringBuilder = new StringBuilder(Integer.toString(averageFlow));
        String result = stringBuilder.substring(0,1) + "." + stringBuilder.substring(1);
        return result;
    }

    private String calculatePendingMileage() {
        int lastVolume = fuelDataList.get(fuelDataList.size()-1).getFuelVolume();
        int pendingMileage = lastVolume*10000/averageFlow;
        return Integer.toString(pendingMileage);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FuelDataViewModel mViewModel = ViewModelProviders.of((FragmentActivity) context).get(FuelDataViewModel.class);
        fuelDataList = mViewModel.getFuelDataList().getValue();
    }

}
