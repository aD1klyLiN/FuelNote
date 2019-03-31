package ml.lylin.utils.fueldata.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import ml.lylin.utils.fueldata.R;
import ml.lylin.utils.fueldata.db.FuelData;
import ml.lylin.utils.fueldata.viewmodel.FuelDataViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String YEAR = "YEAR";
    public static final String MONTH = "MONTH";
    public static final String DAY_OF_MONTH = "DAY_OF_MONTH";
    public static final String MILEAGE = "MILEAGE";
    public static final String FUEL_VOLUME = "FUEL_VOLUME";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView tvDate;
    private Button btnDatePicker;
    private EditText etMileage;
    private Spinner spFuelVolume;
    private Button btnWrite;

    Calendar calendar;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        //---загружаем макет---
        View fragmentView = inflater.inflate(R.layout.fragment_add, container, false);

        tvDate = fragmentView.findViewById(R.id.tvDate);
        btnDatePicker = fragmentView.findViewById(R.id.btnDatePicker);
        etMileage = fragmentView.findViewById(R.id.etMileage);
        spFuelVolume  = fragmentView.findViewById(R.id.spFuelVolume);
        btnWrite = fragmentView.findViewById(R.id.btnWrite);

        //---загружаем дату---
        calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (savedInstanceState != null) {
            mYear = savedInstanceState.getInt(YEAR);
            mMonth = savedInstanceState.getInt(MONTH);
            mDay = savedInstanceState.getInt(DAY_OF_MONTH);
        }
        tvDate.setText(setDateText(mYear, mMonth, mDay));

        //---создаём диалог для даты---
        final DatePickerDialog datePickerDialog = new DatePickerDialog(container.getContext(),
                R.style.ThemeOverlay_AppCompat_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvDate.setText(setDateText(year, month, dayOfMonth));
                        calendar.set(year, month, dayOfMonth);
                    }
                },
                mYear,
                mMonth,
                mDay);
        datePickerDialog.create();
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        if (savedInstanceState != null) {
            etMileage.setText(savedInstanceState.getString(MILEAGE));
        }

        String[] spItems = getActivity().getResources().getStringArray(R.array.fuel_volume_values);
        spFuelVolume.setAdapter(new ArrayAdapter<>(container.getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                spItems));
        spFuelVolume.setSelection(5);
        if (savedInstanceState != null) {
            spFuelVolume.setSelection((savedInstanceState.getInt(FUEL_VOLUME)));
        }

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date = calendar;
                int mileage = Integer.parseInt(etMileage.getText().toString());
                int fuelVolume = Integer.parseInt(spFuelVolume.getSelectedItem().toString());
                FuelData fuelData = new FuelData(date, mileage, fuelVolume);
                mListener.onBtnWritePressed(fuelData);
            }
        });

        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
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


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private String setDateText(int year, int month, int dayOfMonth) {
        String dateText = Integer.toString(dayOfMonth);
        String[] monthArray = getActivity().getResources().getStringArray(R.array.names_of_months);
        dateText = dateText.concat(" - ").concat(monthArray[month]).concat(" - ").concat(Integer.toString(year));
        return dateText;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(YEAR, calendar.get(Calendar.YEAR));
        outState.putInt(MONTH, calendar.get(Calendar.MONTH));
        outState.putInt(DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        outState.putString(MILEAGE, etMileage.getText().toString());
        outState.putInt(FUEL_VOLUME, (int) spFuelVolume.getSelectedItemId());
        super.onSaveInstanceState(outState);
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

        void onBtnWritePressed(FuelData fuelData);
    }
}
