package ml.lylin.utils.fueldata.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import ml.lylin.utils.fueldata.R
import ml.lylin.utils.fueldata.databinding.FragmentAdd2Binding
import ml.lylin.utils.fueldata.db2.FillingRecord
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModel
import java.util.*

class AddFragment: Fragment() {

    /*private var calendar = Calendar.getInstance()
    private var day = calendar.get(Calendar.DAY_OF_MONTH)
    private var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    private var month = calendar.get(Calendar.MONTH)
    private var year = calendar.get(Calendar.YEAR)
    private var mileage = 0
    private var volume = 30*/

    private lateinit var appViewModel: AppViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView: View = inflater.inflate(R.layout.fragment_add2, container, false)
        val addFragmentBinding: FragmentAdd2Binding = FragmentAdd2Binding.bind(fragmentView)
        appViewModel.fragmentData.observe(this, androidx.lifecycle.Observer {
            onRecordChanged(addFragmentBinding, it)
        })

        addFragmentBinding.btnChangeDate.setOnClickListener(this::onDateChangeClick)
        addFragmentBinding.tvMileage.setOnClickListener(this::onMileageChangeClick)
        ArrayAdapter.createFromResource(context!!, R.array.fuel_volume_values, R.layout.spinner_list_item).also {
            addFragmentBinding.spFuelVolume.adapter = it
        }
        addFragmentBinding.spFuelVolume.setSelection(5)
        return fragmentView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        appViewModel = ViewModelProviders.of(context as FragmentActivity).get(AppViewModel::class.java)
    }

    /**
     * функция для обсервера; подписывает лейаут на изменения в экземпляре [FillingRecord]
     * @param addFragmentBinding - биндинг для фрагмента, автосгенерированный; в нём есть ссылка на вьюмодель, которая внутри лейаута
     * @param fragmentDataValue - экземпляр [FillingRecord] для чтения и редактирования новой записи; берётся из вьюмодели, обёрнут в лайвдату
     */
    private fun onRecordChanged(addFragmentBinding: FragmentAdd2Binding, fragmentDataValue: Map<String,Int?>) {
        addFragmentBinding.apply {
            this.day = if (fragmentDataValue.getValue("day")!! > 9) fragmentDataValue["day"].toString() else "0" + fragmentDataValue["day"].toString()
            this.dayOfWeek = resources.getStringArray(R.array.names_of_week)[fragmentDataValue.getValue("day_of_week")!!]
            this.month = resources.getStringArray(R.array.names_of_months)[fragmentDataValue.getValue("month")!!]
            this.mileage = fragmentDataValue.getValue("mileage").toString()
        }
    }

    private fun onDateChangeClick(v: View) {
        val dateChangeDialog = DateChangeDialog()
        dateChangeDialog.show(fragmentManager, "date_change_dialog")
    }

    private fun onMileageChangeClick(v: View) {
        val mileageChangeDialog = MileageChangeDialog()
        mileageChangeDialog.show(fragmentManager, "mileage_change_dialog")
    }

    private fun onVolumeChangeClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

    }

    /*companion object {

        fun newInstance() = AddFragment

    }*/

}
