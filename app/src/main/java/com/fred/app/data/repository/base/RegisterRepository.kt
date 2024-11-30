package com.fred.app.data.repository.base

import com.fred.app.data.repository.model.User
import com.fred.app.util.State

interface RegisterRepository {

  suspend fun register(
      userId: String,
      username: String?,
      name: String?,
      phone: String?,
      mail: String?,
      address: String?,
      gender: Boolean?,
  ): State<User>
}
