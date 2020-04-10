package bme.project.fmea

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import bme.project.fmea.adddevice.AddDeviceActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabAdd.setOnClickListener {
            if (bottomNav.selectedItemId == R.id.nav_devices){
                startActivity(Intent(this,AddDeviceActivity::class.java))
            }
        }
    }
}
