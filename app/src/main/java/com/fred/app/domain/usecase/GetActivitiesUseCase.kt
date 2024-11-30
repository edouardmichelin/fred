package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.ActivityRepository
import com.fred.app.data.repository.model.Activity
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetActivitiesUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val activityRepository: ActivityRepository,
) {

  suspend operator fun invoke(): Flow<State<List<Activity>>> {
    return authService.userId?.let {
      activityRepository.getAllActivitiesOf(it)
    } ?: flowOf(State.Error(Exception("")))
  }
}
