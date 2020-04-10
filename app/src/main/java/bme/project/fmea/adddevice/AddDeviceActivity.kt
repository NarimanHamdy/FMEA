package bme.project.fmea.adddevice

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import bme.project.fmea.DEVICES_DOCUMENT
import bme.project.fmea.R
import bme.project.fmea.ext.gone
import bme.project.fmea.ext.visible
import bme.project.fmea.models.Device
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_device.*
import java.lang.Exception
import java.util.*

class AddDeviceActivity : AppCompatActivity() {
    var selectedId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)
        title = "Add New Device"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            selectedId = checkedId
        }
        btnSave.setOnClickListener {
            createDevice()?.let {
                startLoading()

                FirebaseFirestore.getInstance().collection(DEVICES_DOCUMENT).document().set(it)
                    .addOnCompleteListener {
                        endLoading()
                    }
                    .addOnSuccessListener {
                        setResult(Activity.RESULT_OK)
//                        finish()
                    }
                    .addOnFailureListener(this::showFailure)
            }
        }
    }

    private fun showFailure(e: Exception) {
        Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
    }

    private fun hideFailure() {

    }

    private fun startLoading() {
        hideFailure()
        btnSave.gone()
        progress.visible()
    }

    private fun endLoading() {
        btnSave.visible()
        progress.gone()
    }

    fun createDevice(): Device? {
        var isValid = true
        if (etName.text.isNullOrEmpty()) {
            etName.error = "Please Add the Name"
            isValid = false
        }
        if (etBrand.text.isNullOrEmpty()) {
            etBrand.error = "Please Add the Brand"
            isValid = false
        }
        if (etModel.text.isNullOrEmpty()) {
            etModel.error = "Please Add the Model"
            isValid = false
        }
        if (etDepartment.text.isNullOrEmpty()) {
            etDepartment.error = "Please Add the Department"
            isValid = false
        }
        if (etPrice.text.isNullOrEmpty()) {
            etPrice.error = "Please Add the Purchasing Price"
            isValid = false
        }
        if (etPriceOfContract.text.isNullOrEmpty()) {
            etPriceOfContract.error = "Please Add the Price of Service Contract"
            isValid = false
        }
        if (selectedId == -1) {
            Toast.makeText(this, "Select type of contract", Toast.LENGTH_LONG).show()
            isValid = false
        }

        val date = Calendar.getInstance().apply {
            set(
                datePicker.year,
                datePicker.month,
                datePicker.dayOfMonth
            )
        }.time

        return if (isValid) Device(
            etName.text(),
            etPrice.text(),
            etModel.text(),
            etDepartment.text(),
            findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString(),
            date,
            etBrand.text(),
            etPriceOfContract.text()
        )
        else null
    }

    private fun AppCompatEditText.text() = text.toString()

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
