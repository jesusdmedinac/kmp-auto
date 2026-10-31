package io.github.kmpauto.sample.model

public data class ChargingStation(
    val id: String,
    val name: String,
    val powerKw: Int,
    val availablePlugs: Int,
    val totalPlugs: Int,
    val address: String,
    val pricePerKwh: String
)
