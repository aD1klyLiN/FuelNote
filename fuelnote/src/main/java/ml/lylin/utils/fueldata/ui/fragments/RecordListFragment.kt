package ml.lylin.utils.fueldata.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ml.lylin.utils.fueldata.R
import ml.lylin.utils.fueldata.db.FillingRecord
import ml.lylin.utils.fueldata.ui.viewmodel.AppViewModel

class RecordListFragment : Fragment() {

    private val appViewModel by lazy {
        ViewModelProviders.of(this).get(AppViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.run {
            this.inflate(R.layout.fragment_list, container, false)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvList)
        val listAdapter = RecordListAdapter(context!!) //listOf(appViewModel.record.value!!.record)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = listAdapter

        appViewModel.recordList.observe(this, Observer {
            if (it.size < 10) {
                listAdapter.list = it
            } else {
                listAdapter.list = it //listAdapter.list = it.subList(0, 10)
            }
        })

        return view
    }

    private inner class RecordListAdapter(val context: Context): RecyclerView.Adapter<RecordListViewHolder>() {

        var list = emptyList<FillingRecord>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordListViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.fragment_list_item, parent, false)
            //view.setOnClickListener { appViewModel.delete(view.) } //Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
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

    private inner class RecordListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(record: FillingRecord) {
            itemView.findViewById<TextView>(R.id.tvDate).text = StringBuilder(if (record.day > 9) record.day.toString() else "0" + record.day.toString())
                    .append("/")
                    .append(if (record.month+1 > 9) (record.month+1).toString() else "0" + (record.month+1).toString())
                    .toString()

            itemView.findViewById<TextView>(R.id.tvMileage).text = record.mileage.toString()
            itemView.findViewById<TextView>(R.id.tvFuelVolume).text = if (record.fuelVolume > 9) record.fuelVolume.toString() else " " + record.fuelVolume.toString()
            itemView.setOnLongClickListener {
                MaterialAlertDialogBuilder(context)
                        .setMessage("Delete this record?")
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            appViewModel.delete(record)
                            Toast.makeText(context, "Record " + record.mileage.toString() + " deleted", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ ->  }
                        .show()

                true
            }
        }

    }

}