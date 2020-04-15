package bme.project.fmea.devicedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bme.project.fmea.R
import bme.project.fmea.models.Device
import kotlinx.android.synthetic.main.activity_show_device_details.*
import java.text.SimpleDateFormat
import java.util.*

class ShowDeviceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_device_details)

        title = "Device Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val device = intent.getParcelableExtra<Device>("device")!!
        populateData(device)
    }

    private fun populateData(device: Device) {
        val simpleFormatter = SimpleDateFormat("yyyy-MM-dd")
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
}
