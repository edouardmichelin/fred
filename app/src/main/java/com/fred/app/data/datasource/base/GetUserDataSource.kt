package com.fred.app.data.datasource.base

import com.fred.app.data.repository.model.User
import com.fred.app.util.State

interface GetUserDataSource {

  suspend fun getUserById(userId: String): State<User>
}
