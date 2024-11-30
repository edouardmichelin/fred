package com.fred.app.data.repository.base

import com.fred.app.data.repository.model.User
import com.fred.app.util.State

interface GetUserRepository {

  suspend fun getUserById(userId: String): State<User>
}
