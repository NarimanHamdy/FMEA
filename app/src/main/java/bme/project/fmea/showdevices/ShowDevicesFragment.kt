package bme.project.fmea.showdevices

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import bme.project.fmea.DEVICES_DOCUMENT
import bme.project.fmea.R
import bme.project.fmea.ext.gone
import bme.project.fmea.ext.toast
import bme.project.fmea.ext.visible
import bme.project.fmea.models.Device
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_show_devices.*
import java.lang.Exception

class ShowDevicesFragment : Fragment(R.layout.fragment_show_devices) {

    private val adapter = DevicesAdapter {
        // TODO
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
        FirebaseFirestore.getInstance().collection(DEVICES_DOCUMENT).get()
            .addOnCompleteListener {
                endLoading()
            }
            .addOnFailureListener {
                showFailure(it)
            }
            .addOnSuccessListener {
                val devices = it.toObjects(Device::class.java)
                Log.d("TAG", "devices : $devices")
                adapter.submitList(it.toObjects(Device::class.java))
            }
    }
}
