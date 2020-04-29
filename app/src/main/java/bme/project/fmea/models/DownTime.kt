package bme.project.fmea.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DownTime(val days: Int = 0) : Parcelable