package com.fred.app.data.repository.model

data class Location(
    val id: String = "",
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val ownerId: String = ""
)
