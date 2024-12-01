package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.ActivityRepository
import com.fred.app.data.repository.base.UserRepository
import com.fred.app.data.repository.base.VehicleRepository
import com.fred.app.data.repository.model.Activity
import com.fred.app.data.repository.model.ActivityType
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CreateActivityUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val userRepository: UserRepository,
    private val vehicleRepository: VehicleRepository,
    private val activityRepository: ActivityRepository,
) {

  suspend operator fun invoke(
      type: ActivityType,
      distance: Float,
      vehicleId: String,
      impact: Int? = null,
      description: String = ""
  ): Flow<State<Activity>> = flow {
      authService.userId?.let { id ->
        userRepository.getUserById(id).collect { user ->
            when (user) {
                is State.Loading -> emit(State.Loading)
                is State.Error -> emit(State.Error(user.exception))
                is State.Success -> {
                    vehicleRepository.getVehicleById(vehicleId).collect { vehicle ->
                        when (vehicle) {
                            is State.Loading -> emit(State.Loading)
                            is State.Error -> emit(State.Error(vehicle.exception))
                            is State.Success -> {
                                val vehicleData = vehicle.data

                                // Calculate the impact of the activity
                                activityRepository.createActivity(
                                    type = type,
                                    distance = distance,
                                    vehicleId = vehicleId,
                                    impact = impact ?: (vehicleData.carbonFootprint * distance).toInt(),
                                    ownerId = id,
                                    description = description
                                ).collect { activity ->
                                    when (activity) {
                                        is State.Success ->
                                            userRepository.updateScoreBy(id, activity.data.impact)
                                                .collect { user ->
                                                    when (user) {
                                                        is State.Success -> emit(activity)
                                                        is State.Error -> emit(State.Error(user.exception))
                                                        is State.Loading -> emit(State.Loading)
                                                    }
                                                }
                                        else -> emit(activity)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    } ?: flowOf(State.Error(Exception("")))
  }
}
