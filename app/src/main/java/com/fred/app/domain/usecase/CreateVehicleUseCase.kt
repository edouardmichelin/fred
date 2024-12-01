package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.VehicleRepository
import com.fred.app.data.repository.model.FuelType
import com.fred.app.data.repository.model.Vehicle
import com.fred.app.data.repository.model.VehicleType
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CreateVehicleUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val vehicleRepository: VehicleRepository,
) {

  suspend operator fun invoke(
      type: VehicleType,
      name: String,
      fuelType: FuelType,
      age: Int,
      km: Int,
      carbonFootprint: Double,
  ): Flow<State<Vehicle>> {
    return authService.userId?.let {
      vehicleRepository.createVehicle(
          type = type,
          name = name,
          fuelType = fuelType,
          age = age,
          km = km,
          carbonFootprint = carbonFootprint,
          ownerId = it)
    } ?: flowOf(State.Error(Exception("")))
  }
}
