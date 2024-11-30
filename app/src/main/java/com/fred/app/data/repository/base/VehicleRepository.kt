package com.fred.app.data.repository.base

import com.fred.app.data.repository.model.FuelType
import com.fred.app.data.repository.model.Vehicle
import com.fred.app.data.repository.model.VehicleType
import com.fred.app.util.State

interface VehicleRepository {
    suspend fun createVehicle(
        type: VehicleType,
        name: String,
        fuelType: FuelType,
        age: Int,
        km: Int,
        carbonFootprint: Double,
        ownerId: String,
    ): State<Vehicle>

    suspend fun getAllVehicles(): State<List<Vehicle>>
}