package bme.project.fmea.showdevices

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bme.project.fmea.R
import bme.project.fmea.models.Device
import kotlinx.android.synthetic.main.item_device.view.*

class DevicesAdapter(private val onItemClicked: (Device) -> Unit) :
    ListAdapter<Device, DevicesAdapter.ViewHolder>(Device) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_device,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position), onItemClicked)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(item: Device, onItemClicked: (Device) -> Unit) {
            itemView.setOnClickListener {
                onItemClicked(item)
            }
            itemView.apply {
                tvName.text = item.name
                tvBrand.text = item.brand
                tvModel.text = item.model
            }
        }
    }

}