package ml.lylin.utils.fueldata.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_stat.*
import ml.lylin.utils.fueldata.R
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModel

class StatFragment: Fragment() {

    private val appViewModel by lazy {
        ViewModelProviders.of(this).get(AppViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.run {
            this.inflate(R.layout.fragment_stat, container, false)
        }

        appViewModel.recordList.observe(this, Observer {
            val listSize = if (it.size < 5) it.size else 5
            val subList = it.subList(0, listSize)
            var average = 0
            for (i in it) average += i.mileage
            average = average / 5
        })

        return view
    }

}