package ml.lylin.utils.fueldata.ui.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModel

class DateChangeDialog: DialogFragment() {

    private lateinit var appViewModel: AppViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        appViewModel = ViewModelProviders.of(context as FragmentActivity).get(AppViewModel::class.java)
        return DatePickerDialog(activity as Context,
                context as DatePickerDialog.OnDateSetListener,
                appViewModel.record.value!!.year,
                appViewModel.record.value!!.month,
                appViewModel.record.value!!.day)
    }

}




