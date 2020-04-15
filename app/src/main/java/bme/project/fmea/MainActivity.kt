package bme.project.fmea

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import bme.project.fmea.adddevice.AddDeviceActivity
import bme.project.fmea.showdevices.ShowDevicesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentFragment: String? = null

    private val devicesFragment = ShowDevicesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabAdd.setOnClickListener {
            if (bottomNav.selectedItemId == R.id.nav_devices) {
                startActivity(Intent(this, AddDeviceActivity::class.java))
            }
        }
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.title) {
                getString(R.string.devices) -> {
                    showFragment(devicesFragment, DEVICE_FRAGMENT)
                }
                getString(R.string.analysis) -> {

                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        bottomNav.selectedItemId = getTab()
    }

    private fun getTab(): Int {
        currentFragment?.let {
            if (currentFragment == ANALYSIS_FRAGMENT) return R.id.nav_reports
        }
        return R.id.nav_devices
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        if (getFragmentByTag(currentFragment) != null) {
            supportFragmentManager.beginTransaction().hide(getFragmentByTag(currentFragment)!!)
                .commit()
        }

        currentFragment = tag

        if (getFragmentByTag(tag) != null) {
            //if the fragment exists, show it.
            supportFragmentManager.beginTransaction().show(getFragmentByTag(tag)!!)
                .commit()
        } else {
            //if the fragment does not exist, add it to fragment manager.
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag).commit()
        }
    }

    private fun getFragmentByTag(tag: String?): Fragment? {
        return supportFragmentManager.findFragmentByTag(tag)
    }

    companion object {
        const val DEVICE_FRAGMENT = "device"
        const val ANALYSIS_FRAGMENT = "analysis"
    }
}
