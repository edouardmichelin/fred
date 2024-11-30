package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.LocationRepository
import com.fred.app.data.repository.model.Location
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CreateLocationUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val locationRepository: LocationRepository,
) {

  suspend operator fun invoke(
      name: String,
      latitude: Double,
      longitude: Double
  ): Flow<State<Location>> {
    return authService.userId?.let {
        locationRepository.createLocation(
            name = name,
            latitude = latitude,
            longitude = longitude,
            ownerId = it
        )
    } ?: flowOf(State.Error(Exception("")))
  }
}