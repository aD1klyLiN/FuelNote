package ml.lylin.utils.fueldata.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ml.lylin.utils.fueldata.AppRepository

class AppViewModelFactory(
        private val appRepository: AppRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AppViewModel(appRepository) as T
    }

}