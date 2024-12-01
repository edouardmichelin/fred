package com.fred.app.domain.usecase

import android.util.Log
import com.fred.app.data.repository.base.ActivityRepository
import com.fred.app.data.repository.base.LocationRepository
import com.fred.app.data.repository.base.UserRepository
import com.fred.app.data.repository.base.VehicleRepository
import com.fred.app.data.repository.model.User
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class GetUserUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val userRepository: UserRepository,
    private val vehicleRepository: VehicleRepository,
    private val activityRepository: ActivityRepository,
    private val locationRepository: LocationRepository
) {

  suspend operator fun invoke(): Flow<State<User>> = flow {
    authService.userId?.let { id ->
      userRepository.getUserById(id).collect { user ->
        when (user) {
          is State.Success -> {
            vehicleRepository.getAllVehiclesOf(id).collect { vehicles ->
              when (vehicles) {
                is State.Loading -> emit(State.Loading)
                is State.Error -> emit(State.Error(vehicles.exception))
                is State.Success -> {
                  locationRepository.getAllLocationsOf(id).collect { locations ->
                    Log.d("GetUserUseCase", "Locations: $locations")
                    when (locations) {
                      is State.Loading -> emit(State.Loading)
                      is State.Error -> emit(State.Error(locations.exception))
                      is State.Success -> {
                        activityRepository.getAllActivitiesOf(id).collect { activities ->
                          Log.d("GetUserUseCase", "Activities: $activities")
                          when (activities) {
                            is State.Loading -> emit(State.Loading)
                            is State.Error -> emit(State.Error(activities.exception))
                            is State.Success ->
                                emit(
                                    State.Success(
                                        user.data.copy(
                                            transportations = vehicles.data,
                                            locations = locations.data,
                                            activities = activities.data)))
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
