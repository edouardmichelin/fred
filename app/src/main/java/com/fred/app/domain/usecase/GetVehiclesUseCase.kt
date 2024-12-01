package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.VehicleRepository
import com.fred.app.data.repository.model.Vehicle
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetVehiclesUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val vehicleRepository: VehicleRepository,
) {

  suspend operator fun invoke(): Flow<State<List<Vehicle>>> {
    return authService.userId?.let { vehicleRepository.getAllVehiclesOf(it) }
        ?: flowOf(State.Error(Exception("")))
  }
}
