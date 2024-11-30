package com.fred.app.data.repository.model

data class Vehicle(
    val id: String = "",
    val type: VehicleType = VehicleType.Walk,
    val name: String = type.name,
    val fuelType: FuelType = FuelType.Muscle,
    val age: Int = 0,
    val km: Int = 0,
    val carbonFootprint: Double = 0.0,
    val ownerId: String = ""
)
