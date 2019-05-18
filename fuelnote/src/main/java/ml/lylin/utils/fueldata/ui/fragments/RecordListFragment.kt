package ml.lylin.utils.fueldata.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ml.lylin.utils.fueldata.R
import ml.lylin.utils.fueldata.db.FillingRecord
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModel

class RecordListFragment : Fragment() {

    private val appViewModel by lazy {
        ViewModelProviders.of(this).get(AppViewModel::class.java)
    }

    /*override fun onAttach(context: Context?) {
        super.onAttach(context)
        appViewModel = ViewModelProviders.of(context as FragmentActivity).get(AppViewModel::class.java)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.run {
            this.inflate(R.layout.fragment_list, container, false)
        }
        val listLayoutManager = LinearLayoutManager(context)
        val listAdapter = RecordListAdapter(appViewModel.recordList) //listOf(appViewModel.record.value!!.record)
        appViewModel.recordList.observe(this, Observer {
            listAdapter.notifyDataSetChanged()
        })

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvList)
        recyclerView.layoutManager = listLayoutManager
        recyclerView.adapter = listAdapter

        return view
    }

    private class RecordListAdapter(val recordList: LiveData<ArrayList<FillingRecord>>): RecyclerView.Adapter<RecordListViewHolder>() {

        private val list = recordList.value ?: arrayListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordListViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false)
            return RecordListViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RecordListViewHolder, position: Int) {
            val record = list[position]
            holder.bind(record)
        }

    }

    private class RecordListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(record: FillingRecord) {
            itemView.findViewById<TextView>(R.id.tvDate).text = record.day.toString() + "/" + (record.month+1).toString()
            itemView.findViewById<TextView>(R.id.tvMileage).text = record.mileage.toString()
            itemView.findViewById<TextView>(R.id.tvFuelVolume).text = record.fuelVolume.toString()
        }

    }

}