package com.fred.app.data.repository.base

import com.fred.app.data.repository.model.Location
import com.fred.app.util.State

interface LocationRepository {
    suspend fun createLocation(
        name: String,
        latitude: Double,
        longitude: Double,
    ): State<Location>

    suspend fun getAllLocations(): State<List<Location>>
}