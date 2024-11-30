package com.fred.app.data.datasource.base

import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.util.State

interface RegisterDataSource {

  suspend fun register(
      userId: String,
      username: String?,
      name: String?,
      mail: String?,
      address: Location?,
      gender: Gender?,
  ): State<User>
}
