package com.fred.app.data.repository.base

import com.fred.app.data.repository.model.Activity
import com.fred.app.data.repository.model.ActivityType
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    suspend fun createActivity(
        type: ActivityType,
        distance: Float,
        timestamp: Long,
        vehicleId: String,
        impact: Int,
        ownerId: String
    ): Flow<State<Activity>>

    suspend fun getAllActivitiesOf(userId: String): Flow<State<List<Activity>>>
}