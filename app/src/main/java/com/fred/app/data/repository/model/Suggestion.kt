package com.fred.app.data.repository.model

data class Suggestion(
    val title: String = "",
    val description: String = "",
    val activity: Activity = Activity(),
    val impact: Int = 50,
)
