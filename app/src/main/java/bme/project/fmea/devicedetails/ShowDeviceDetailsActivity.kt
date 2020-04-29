package bme.project.fmea.devicedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import bme.project.fmea.R
import bme.project.fmea.models.Device
import bme.project.fmea.models.DownTime
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_show_device_details.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

class ShowDeviceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_device_details)

        title = "Device Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val device = intent.getParcelableExtra<Device>("device")!!
        populateData(device)
        calculateAnalysis(device)
        fabAdd.setOnClickListener {
            createChooseQuantityDialog {
                val task = FirebaseFirestore.getInstance().document(device.path)
                    .update("downTimes", FieldValue.arrayUnion(DownTime(it)))
                task.addOnSuccessListener {
                    FirebaseFirestore.getInstance().document(device.path).get()
                        .addOnSuccessListener { snapshot ->
                            snapshot.toObject(Device::class.java)?.let {
                                it.path = snapshot.reference.path
                                calculateAnalysis(it)
                            }
                        }.addOnCompleteListener {

                        }
                }.addOnCompleteListener {

                }
            }
        }
    }

    private val unavailabilityLevels = arrayOf("Very Low", "Low", "Moderate", "High", "Very High")
    private fun calculateAnalysis(device: Device) {
        if (device.downTimes.isNotEmpty()) {
            val totalDownTime =
                device.downTimes.reduce { acc, downTime -> acc.copy(days = acc.days + downTime.days) }
            val unavailability = (((totalDownTime.days / 365.0 * 100) * 100).toInt()) / 100.0
            val level = if (unavailability >= 50) {
                4
            } else {
                max(unavailability.toInt() / 10 - 1, 0)
            }
            val text =
                "Out of service days : ${totalDownTime.days}\nTotal Failures : ${device.downTimes.size}" +
                        "\nUnavailability : $unavailability" +
                        "\nLevel : ${unavailabilityLevels[level]}"
            tvUnavailability.text = text
        }
    }

    private fun populateData(device: Device) {
        val simpleFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        tvName.text = getString(R.string.name_value, device.name)
        tvBrand.text = getString(R.string.brand_value, device.brand)
        tvModel.text = getString(R.string.model_value, device.model)
        tvPurchasingDate.text =
            getString(R.string.purchasing_date_value, simpleFormatter.format(device.purchaseDate))
        tvPurchasingPrice.text = getString(R.string.purchasing_price_value, device.price)
        tvDepartment.text = getString(R.string.department_value, device.department)
        tvTypeOfContract.text = getString(R.string.type_of_contract_value, device.serviceContract)
        tvPriceOfContract.text =
            getString(R.string.price_of_contract_value, device.priceOfServiceContract)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun createChooseQuantityDialog(callback: (Int) -> Unit) {
        val editText = EditText(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    50f,
                    context.resources.displayMetrics
                ).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER
            inputType = InputType.TYPE_CLASS_NUMBER
        }
        val dialog = AlertDialog.Builder(this)
            .setTitle("Enter Number of down time days")
            .setView(editText)
            .setPositiveButton("Accept", null)
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (editText.text.isNullOrEmpty() || editText.text.toString().toInt() == 0) {
                    editText.error = "Enter a valid quantity"
                } else {
                    val count = editText.text.toString().toInt()
                    callback(count)
                    dialog.dismiss()
                }
            }
        }
        dialog.apply {
            window?.decorView?.layoutDirection = View.LAYOUT_DIRECTION_LOCALE
            show()
        }
    }
}
