package ml.lylin.utils.fueldata.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ml.lylin.utils.fueldata.R
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModel
import java.util.*

class MileageChangeDialog: DialogFragment()/*, View.OnClickListener */{

    private lateinit var listener: MileageChangeListener
    /*var mileage: String = ""
    var count: Int = 0
*/
    private lateinit var appViewModel: AppViewModel


    interface MileageChangeListener {

        fun onMileageChanged(dialog: AlertDialog)

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.dialog_change_number_plain, null))
                    .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                        listener.onMileageChanged(this.dialog as AlertDialog)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")


        /*val builder = AlertDialog.Builder(context!!)
        //val inflater = requireActivity().layoutInflater
        *//*view.findViewById<MaterialButton>(R.id.button_1)!!.setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_2)!!.setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_3)!!.setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_4)!!.setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_5)!!.setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_6)!!.setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_7)!!.setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_8)!!.setOnClickListener(this)
        view.findViewById<MaterialButton>(R.id.button_9)!!.setOnClickListener(this)*//*
        builder.setView(R.layout.dialog_change_number_plain)
                .setPositiveButton("Confirm") { dialog, which ->
                    listener.onMileageChanged(this)
                    *//*val st1 = this.view?.findViewById<TextInputEditText>(R.id.etMileage)?.text.toString()
                    val st = st1.toInt()
                    val dataMap = HashMap<String,Int?>()
                    dataMap["day"] = appViewModel.fragmentData.value?.get("day")
                    dataMap["month"] = appViewModel.fragmentData.value?.get("month")
                    dataMap["day_of_week"] = appViewModel.fragmentData.value?.get("day_of_week")
                    if (st != 0) dataMap["mileage"] = st else dataMap["mileage"] = 0
                    dataMap["fuel_volume"] = appViewModel.fragmentData.value?.get("fuel_volume")
                    appViewModel.fragmentData.postValue(dataMap)*//*
                } //todo: заменить текст на ресурс, написать обработчик
        val dialog = builder.create()
        //dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", activity as DialogInterface.OnClickListener)
        return dialog*/
    }

    /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_change_number_plain, container, false)

        return view
    }*/

    override fun onAttach(context: Context?){
        super.onAttach(context)
        try {
            listener = context as MileageChangeListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement MileageChangeListener"))
        }
        //this.view?.findViewById<MaterialButton>(R.id.button_0)?.setOnClickListener(this)
        appViewModel = ViewModelProviders.of(context as FragmentActivity).get(AppViewModel::class.java)
        val view= this.view
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val st = view.findViewById<TextInputEditText>(R.id.etMileage).text.toString()
    }*/

    /*override fun onClick(v: View) {
        when(v) {
            this.view!!.findViewById<MaterialButton>(R.id.button_0) -> addNumber(0)
            this.view!!.findViewById<MaterialButton>(R.id.button_0) -> addNumber(0)
            this.view!!.findViewById<MaterialButton>(R.id.button_1) -> addNumber(1)
            this.view!!.findViewById<MaterialButton>(R.id.button_2) -> addNumber(2)
            this.view!!.findViewById<MaterialButton>(R.id.button_3) -> addNumber(3)
            this.view!!.findViewById<MaterialButton>(R.id.button_4) -> addNumber(4)
            this.view!!.findViewById<MaterialButton>(R.id.button_5) -> addNumber(5)
            this.view!!.findViewById<MaterialButton>(R.id.button_6) -> addNumber(6)
            this.view!!.findViewById<MaterialButton>(R.id.button_7) -> addNumber(7)
            this.view!!.findViewById<MaterialButton>(R.id.button_8) -> addNumber(8)
            this.view!!.findViewById<MaterialButton>(R.id.button_9) -> addNumber(9)
        }
        this.view!!.findViewById<TextView>(R.id.tvMileage).text = mileage
    }

    private fun addNumber(number: Int) {
        if (count <= 5) {
            mileage += number.toString()
            count++
        }
    }*/

}
