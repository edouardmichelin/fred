package com.fred.app.data.repository.base

import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.LocationType
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
  suspend fun createLocation(
      name: String,
      latitude: Double,
      longitude: Double,
      ownerId: String,
      locationType: LocationType,
      country: String,
  ): Flow<State<Location>>

  suspend fun search(query: String): Flow<State<List<Location>>>

  suspend fun getAllLocationsOf(userId: String): Flow<State<List<Location>>>
}
