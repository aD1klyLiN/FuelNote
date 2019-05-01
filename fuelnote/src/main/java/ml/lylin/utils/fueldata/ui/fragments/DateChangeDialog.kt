package ml.lylin.utils.fueldata.ui.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModel
import java.util.*

class DateChangeDialog: DialogFragment() {

    private lateinit var appViewModel: AppViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = DatePickerDialog(activity as Context,
                context as DatePickerDialog.OnDateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        return dialog
    }

    /*Calendar.getInstance().get(Calendar.YEAR),
    Calendar.getInstance().get(Calendar.MONTH),
    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)*/

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        appViewModel = ViewModelProviders.of(context as FragmentActivity).get(AppViewModel::class.java)
    }

}




