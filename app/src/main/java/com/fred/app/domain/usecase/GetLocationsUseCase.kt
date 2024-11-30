package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.LocationRepository
import com.fred.app.data.repository.model.Location
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetLocationsUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val locationRepository: LocationRepository,
) {

  suspend operator fun invoke(): Flow<State<List<Location>>> {
    return authService.userId?.let {
      locationRepository.getAllLocationsOf(it)
    } ?: flowOf(State.Error(Exception("")))
  }
}
