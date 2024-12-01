package com.fred.app.data.repository.base

import com.fred.app.data.repository.model.User
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow

interface UserRepository {

  suspend fun getUserById(userId: String): Flow<State<User>>

  suspend fun updateScoreBy(userId: String, score: Int): Flow<State<User>>
}
