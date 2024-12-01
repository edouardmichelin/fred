package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.ActivityRepository
import com.fred.app.data.repository.base.GetUserRepository
import com.fred.app.data.repository.base.LocationRepository
import com.fred.app.data.repository.base.VehicleRepository
import com.fred.app.data.repository.model.User
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetUserUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val getUserRepository: GetUserRepository,
    private val vehicleRepository: VehicleRepository,
    private val activityRepository: ActivityRepository,
    private val locationRepository: LocationRepository
) {

  suspend fun invoke(): Flow<State<User>> = flow {
    authService.userId?.let { id ->
        getUserRepository.getUserById(id).collect { user ->
          when (user) {
            is State.Success -> {
              vehicleRepository.getAllVehiclesOf(id).collect { vehicles ->
                when (vehicles) {
                  is State.Loading -> emit(State.Loading)
                  is State.Error -> emit(State.Error(vehicles.exception))
                  is State.Success -> {
                    locationRepository.getAllLocationsOf(id).collect { locations ->
                      when (locations) {
                        is State.Loading -> emit(State.Loading)
                        is State.Error -> emit(State.Error(locations.exception))
                        is State.Success -> {
                          activityRepository.getAllActivitiesOf(id).collect { activities ->
                            when (activities) {
                              is State.Loading -> emit(State.Loading)
                              is State.Error -> emit(State.Error(activities.exception))
                              is State.Success -> emit(State.Success(user.data.copy(
                                transportations = vehicles.data,
                                locations = locations.data,
                                activities = activities.data
                                )))
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
            else -> emit(user)
          }
        }

    } ?: flowOf(State.Error(Exception("")))
  }
}
