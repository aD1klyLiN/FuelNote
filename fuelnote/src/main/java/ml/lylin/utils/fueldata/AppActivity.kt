package ml.lylin.utils.fueldata

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.app_activity.*
import ml.lylin.utils.fueldata.databinding.AppActivityBinding
import ml.lylin.utils.fueldata.db.AppDB
import ml.lylin.utils.fueldata.ui.fragments.MileageChangeDialog
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModel
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModelFactory
import java.util.*

class AppActivity : AppCompatActivity(), MileageChangeDialog.MileageChangeListener, DatePickerDialog.OnDateSetListener {

    // экземпляр Вьюмодели; Вьюмодель создаётся через фабрику
    private lateinit var appViewModel: AppViewModel

    // экземпляр НавКонтрола
    /*private val appNavController by lazy {
        Navigation.findNavController(this, R.id.fragment_container)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //вьюмодель запрашивается у провайдера
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        //автогенерированный класс биндинга присоединяет разметку к активити
        val appBinding: AppActivityBinding = DataBindingUtil.setContentView(this, R.layout.app_activity)

        //в биндинг передаётся переменная-вьюмодель
        appBinding.appViewModel = appViewModel

        //навигационный хост - присоединяет контейнер для фрагментов
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment? ?: return

        //контроллер берётся из хоста
        val navController = navHost.navController

        //боковая панель, она связывается с навигационным контроллером
        val sideBar = findViewById<NavigationView>(R.id.nav_view)
        sideBar.setupWithNavController(navController)

        //верхний тулбар
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout = drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    // колбэк для [MileageChangeDialog]
    override fun onMileageChanged(dialog: AlertDialog) {
        val mileageValue = dialog.findViewById<TextInputEditText>(R.id.etMileage).text.toString()
        val record = appViewModel.record.value!!
        val mileageCount = 5
        if (mileageValue.length == mileageCount) record.mileage = mileageValue.toInt() else return
        appViewModel.record.postValue(record)
    }

    // колбэк для [DateChangeDialog]
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val record = appViewModel.record.value!!
        record.day = dayOfMonth
        record.month = month
        record.year = year
        appViewModel.record.postValue(record)
    }

}