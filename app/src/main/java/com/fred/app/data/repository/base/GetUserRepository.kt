package com.fred.app.data.repository.base

import com.fred.app.data.repository.model.User
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow

interface GetUserRepository {

  suspend fun getUserById(userId: String): Flow<State<User>>
}
