package ml.lylin.utils.fueldata.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements FuelDataListAdapter.ItemClickListener{

    private FuelDataListAdapter fuelDataListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
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
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvFuelDataList.setLayoutManager(mLayoutManager);
        rvFuelDataList.setAdapter(fuelDataListAdapter);
        mViewModel.getFuelDataList().observe((LifecycleOwner) getActivity(), fuelDataList -> {
            fuelDataListAdapter.notifyDataSetChanged();
        });
        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(FuelDataViewModel.class);
        fuelDataListAdapter = new FuelDataListAdapter(getFuelDataList(), this);

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

    @Override
    public boolean onLongClick(FuelDataListAdapter.FuelDataListViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        FuelData deletedFuelData = mViewModel.getFuelDataList().getValue().get(position);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("WARNING!!!")
                .setMessage("This item has been deleted!!!")
                .setPositiveButton(android.R.string.ok, (thisDialog, which) -> {
                    deleteItem(deletedFuelData);
                    //fuelDataListAdapter.notifyItemRemoved(position);
                })
                .setNegativeButton(android.R.string.cancel, (thisDialog, which) -> {
                    thisDialog.dismiss();
                })
                .create();
        dialog.show();
        return true;
    }
}
