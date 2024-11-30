package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.ActivityRepository
import com.fred.app.data.repository.model.Activity
import com.fred.app.data.repository.model.ActivityType
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CreateActivityUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val activityRepository: ActivityRepository,
) {

  suspend operator fun invoke(
      type: ActivityType,
      distance: Float,
      timestamp: Long,
      vehicleId: String,
      impact: Int,
  ): Flow<State<Activity>> {
    return authService.userId?.let {
        activityRepository.createActivity(
            type = type,
            distance = distance,
            timestamp = timestamp,
            vehicleId = vehicleId,
            impact = impact,
            ownerId = it
        )
    } ?: flowOf(State.Error(Exception("")))
  }
}
