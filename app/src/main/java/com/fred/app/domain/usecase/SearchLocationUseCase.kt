package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.LocationRepository
import com.fred.app.data.repository.model.Location
import com.fred.app.util.State
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchLocationUseCase
@Inject
constructor(
    private val locationRepository: LocationRepository,
) {
  suspend operator fun invoke(
      query: String,
  ): Flow<State<List<Location>>> {
    return locationRepository.search(query = query)
  }
}
