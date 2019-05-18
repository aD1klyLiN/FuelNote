package ml.lylin.utils.fueldata.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ml.lylin.utils.fueldata.AppRepository
import ml.lylin.utils.fueldata.db.FillingRecordsDAO

class AppViewModelFactory(
        private val fillingRecordsDAO: FillingRecordsDAO
): ViewModelProvider.NewInstanceFactory() {

    /*@Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AppViewModel(fillingRecordsDAO) as T
    }*/

}