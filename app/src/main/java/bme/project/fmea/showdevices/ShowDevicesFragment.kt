package bme.project.fmea.showdevices

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import bme.project.fmea.DEVICES_COLLECTION
import bme.project.fmea.R
import bme.project.fmea.devicedetails.ShowDeviceDetailsActivity
import bme.project.fmea.ext.gone
import bme.project.fmea.ext.toast
import bme.project.fmea.ext.visible
import bme.project.fmea.models.Device
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_show_devices.*
import java.lang.Exception

class ShowDevicesFragment : Fragment(R.layout.fragment_show_devices) {

    private val adapter = DevicesAdapter { device ->
        context?.let {
            val intent = Intent(it, ShowDeviceDetailsActivity::class.java).apply {
                putExtra("device", device)
            }
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startLoading()
        rvDevices.adapter = adapter
        getData()
        refresh.setOnRefreshListener { getData() }
    }

    private fun showFailure(exception: Exception) {
        toast(exception.localizedMessage ?: "Unknown error")
    }


    private fun startLoading() {
        progress?.visible()
    }

    private fun endLoading() {
        progress?.gone()
        refresh.isRefreshing = false

    }

    private fun getData() {
        FirebaseFirestore.getInstance().collection(DEVICES_COLLECTION).get()
            .addOnCompleteListener {
                endLoading()
            }
            .addOnFailureListener {
                showFailure(it)
            }
            .addOnSuccessListener {
                val data = it.documents.map {
                    it.toObject(Device::class.java)!!.apply { path = it.reference.path }
                }
                adapter.submitList(data)
            }
    }
}
