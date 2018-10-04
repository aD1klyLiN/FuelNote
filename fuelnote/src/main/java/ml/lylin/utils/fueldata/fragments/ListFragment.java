package ml.lylin.utils.fueldata.fragments;

import android.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import ml.lylin.utils.fueldata.R;
import ml.lylin.utils.fueldata.db.FuelData;
import ml.lylin.utils.fueldata.viewmodel.FuelDataViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private FuelDataListAdapter fuelDataListAdapter;
    private FuelDataViewModel mViewModel;


    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddFragment.
     */
    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView rvFuelDataList = fragmentView.findViewById(R.id.rvList);
        ImageButton btnRead = fragmentView.findViewById(R.id.btnRead);
        ImageButton btnSave = fragmentView.findViewById(R.id.btnSave);
        btnRead.setOnClickListener(onButtonRead());
        btnSave.setOnClickListener(onButtonSave());
        rvFuelDataList.setAdapter(fuelDataListAdapter);
        rvFuelDataList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        mViewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(FuelDataViewModel.class);
        fuelDataListAdapter = new FuelDataListAdapter(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public View.OnClickListener onButtonRead() {
        mViewModel.getFuelDataFromFile();
        return null;
    }

    public View.OnClickListener onButtonSave() {
        mViewModel.backupFuelDataToFile();
        return null;
    }

    public List<FuelData> getFuelDataList() {
        return mViewModel.getFuelDataList().getValue();
    }

    public void deleteItem(FuelData fuelData) {
        mViewModel.deleteFuelData(fuelData);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        List<FuelData> getFuelDataList();

        void deleteItem(FuelData fuelData);
    }
}
