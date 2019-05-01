package ml.lylin.utils.fueldata

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import ml.lylin.utils.fueldata.databinding.AppActivityBinding
import ml.lylin.utils.fueldata.ui.fragments.MileageChangeDialog
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModel
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModelFactory
import java.util.*

class AppActivity : AppCompatActivity(), MileageChangeDialog.MileageChangeListener, DatePickerDialog.OnDateSetListener {

    // экземпляр Вьюмодели; Вьюмодель создаётся через фабрику
    private val appViewModel by lazy {
        ViewModelProviders.of(this, AppViewModelFactory(AppRepository(application))).get(AppViewModel::class.java)
    }

    // экземпляр НавКонтрола
    private val appNavController by lazy {
        Navigation.findNavController(this, R.id.fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appBinding: AppActivityBinding = DataBindingUtil.setContentView(this, R.layout.app_activity)
        appBinding.appViewModel = appViewModel
    }

    // колбэк для [MileageChangeDialog]
    override fun onMileageChanged(dialog: AlertDialog) {
        val view = dialog.findViewById<TextInputEditText>(R.id.etMileage).text.toString()
        val dataMap = HashMap<String,Int?>()
        dataMap["day"] = appViewModel.fragmentData.value?.get("day")
        dataMap["month"] = appViewModel.fragmentData.value?.get("month")
        dataMap["day_of_week"] = appViewModel.fragmentData.value?.get("day_of_week")
        dataMap["mileage"] = if (dialog.findViewById<TextInputEditText>(R.id.etMileage).text.toString() != "") dialog.findViewById<TextInputEditText>(R.id.etMileage).text.toString().toInt() else 0
        dataMap["fuel_volume"] = appViewModel.fragmentData.value?.get("fuel_volume")
        appViewModel.fragmentData.postValue(dataMap)
    }

    // колбэк для [DateChangeDialog]
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dataMap = HashMap<String,Int?>()
        dataMap["day"] = dayOfMonth
        dataMap["month"] = month
        val calendar = Calendar.getInstance()
        calendar.set(year,month, dayOfMonth)
        dataMap["day_of_week"] = calendar.get(Calendar.DAY_OF_WEEK)
        dataMap["mileage"] = appViewModel.fragmentData.value?.get("mileage")
        dataMap["fuel_volume"] = appViewModel.fragmentData.value?.get("fuel_volume")
        appViewModel.fragmentData.postValue(dataMap)
    }

}