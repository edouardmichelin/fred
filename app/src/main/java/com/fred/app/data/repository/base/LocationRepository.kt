package com.fred.app.data.repository.base

import com.fred.app.data.repository.model.Location
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun createLocation(
        name: String,
        latitude: Double,
        longitude: Double,
        ownerId: String
    ): Flow<State<Location>>

    suspend fun getAllLocations(): Flow<State<List<Location>>>
}