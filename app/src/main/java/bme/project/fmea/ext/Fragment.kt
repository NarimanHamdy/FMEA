package bme.project.fmea.ext

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(text: String) {
    context?.let {
        Toast.makeText(it, text, android.widget.Toast.LENGTH_LONG).show()
    }
}