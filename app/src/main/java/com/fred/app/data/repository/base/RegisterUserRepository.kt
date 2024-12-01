package com.fred.app.data.repository.base

import com.fred.app.data.repository.model.Diet
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.data.repository.model.Vehicle
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow

interface RegisterUserRepository {
  suspend fun register(
      id: String,
      username: String,
      age: Int,
      name: String,
      mail: String,
      avatarId: String,
      gender: Gender,
      address: Location,
      diet: Diet,
      transportations: List<Vehicle>,
      locations: List<Location>,
      score: Int
  ): Flow<State<User>>
}
