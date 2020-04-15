package bme.project.fmea.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Device(
    val name: String = "",
    val price: String = "",
    val model: String = "",
    val department: String = "",
    val serviceContract: String = "",
    val purchaseDate: Date = Date(),
    val brand: String = "",
    val priceOfServiceContract: String = ""
) : Parcelable {
    companion object : DiffUtil.ItemCallback<Device>() {
        override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem == newItem
        }

    }
}