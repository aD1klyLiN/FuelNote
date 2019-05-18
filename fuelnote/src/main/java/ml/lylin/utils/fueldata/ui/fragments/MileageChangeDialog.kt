package ml.lylin.utils.fueldata.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import ml.lylin.utils.fueldata.R
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModel

class MileageChangeDialog: DialogFragment() {

    private lateinit var listener: MileageChangeListener

    private lateinit var appViewModel: AppViewModel


    interface MileageChangeListener {

        fun onMileageChanged(dialog: AlertDialog)

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.dialog_change_number_plain, null))
                    .setPositiveButton("OK") { _, _ ->
                        listener.onMileageChanged(this.dialog as AlertDialog)
                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context?){
        super.onAttach(context)
        try {
            listener = context as MileageChangeListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement MileageChangeListener"))
        }
        appViewModel = ViewModelProviders.of(context as FragmentActivity).get(AppViewModel::class.java)
    }

}
