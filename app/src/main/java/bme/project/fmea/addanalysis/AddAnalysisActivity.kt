package bme.project.fmea.addanalysis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bme.project.fmea.R

class AddAnalysisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_analysis)

        title = "Add New Analysis"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)zz
    }zz
    zz
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
