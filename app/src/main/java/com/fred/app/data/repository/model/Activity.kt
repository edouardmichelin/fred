package com.fred.app.data.repository.model

data class Activity(
    val id: String = "",
    val type: ActivityType = ActivityType.None,
    val distance: Float = 0f,
    val timestamp: Long = System.currentTimeMillis(),
    val transportation: String = "",
    val impact: Int
)
