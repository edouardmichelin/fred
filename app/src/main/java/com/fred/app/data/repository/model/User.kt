package com.fred.app.data.repository.model

data class User(
    val id: String = "",
    val username: String = "",
    val age: Int = 0,
    val name: String = "",
    val mail: String = "",
    val avatarId: Int = 0,
    val gender: Gender = Gender.Other,
    val address: Location? = null,
    val diet: Diet = Diet.Other,
    val transportations: List<Vehicle> = listOf(),
    val locations: List<Location> = listOf(),
    val activities: List<Activity> = listOf(),
    val score: Int = 0
)
