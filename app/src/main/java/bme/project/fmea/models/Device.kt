package bme.project.fmea.models

import java.util.*

data class Device(
    val name: String,
    val price: String,
    val model: String,
    val department: String,
    val serviceContract: String,
    val purchaseDate: Date,
    val brand: String,
    val priceOfServiceContract: String
)