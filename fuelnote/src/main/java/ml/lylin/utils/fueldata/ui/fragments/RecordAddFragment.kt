package ml.lylin.utils.fueldata.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ml.lylin.utils.fueldata.R
import ml.lylin.utils.fueldata.databinding.FragmentAdd2Binding
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModel

class RecordAddFragment: Fragment(), OnItemSelectedListener {

    private lateinit var appViewModel: AppViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        appViewModel = ViewModelProviders.of(context as FragmentActivity).get(AppViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView: View = inflater.inflate(R.layout.fragment_add2, container, false)
        val addFragmentBinding: FragmentAdd2Binding = FragmentAdd2Binding.bind(fragmentView)

        appViewModel.record.observe(this, Observer {
            addFragmentBinding.apply {
                this.day = if (it.day > 9) it.day.toString() else "0" + it.day.toString()
                this.dayOfWeek = resources.getStringArray(R.array.names_of_week)[it.getDayOfWeek()]
                this.month = resources.getStringArray(R.array.names_of_months)[it.month]
                this.mileage = it.mileage.toString()
            }
        })

        addFragmentBinding.btnChangeDate.setOnClickListener(this::onDateChangeClick)
        addFragmentBinding.tvMileage.setOnClickListener(this::onMileageChangeClick)
        addFragmentBinding.btnRecord.setOnClickListener(this::onRecordClick)
        ArrayAdapter.createFromResource(context!!, R.array.fuel_volume_values, R.layout.spinner_list_item).also {
            addFragmentBinding.spFuelVolume.adapter = it
        }
        addFragmentBinding.spFuelVolume.setSelection(5)
        addFragmentBinding.spFuelVolume.onItemSelectedListener = this
        return fragmentView
    }

    private fun onDateChangeClick(v: View) {
        val dateChangeDialog = DateChangeDialog()
        dateChangeDialog.show(fragmentManager, "date_change_dialog")
    }

    private fun onMileageChangeClick(v: View) {
        val mileageChangeDialog = MileageChangeDialog()
        mileageChangeDialog.show(fragmentManager, "mileage_change_dialog")
    }

    private fun onRecordClick(v: View) {
        appViewModel.insert()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        val i = parent!!.selectedItemPosition
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val record = appViewModel.record.value!!
        record.fuelVolume = (position +1) * 5
        appViewModel.record.postValue(record)
    }



    /*private fun onVolumeChangeClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

    }*/

    /*companion object {

        fun newInstance() = RecordAddFragment

    }*/

}
