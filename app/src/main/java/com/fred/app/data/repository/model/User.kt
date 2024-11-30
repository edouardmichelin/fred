package com.fred.app.data.repository.model

data class User(
    val id: String = "",
    val username: String = "",
    val name: String = "",
    val mail: String = "",
    val avatarId: String = "",
    val gender: Gender = Gender.Other,
    val address: Location? = null,
    val diet: Diet = Diet.Other,
    val transportations: List<Vehicle> = listOf(),
    val locations: List<Location> = listOf(),
    val score: Int = 0
)
