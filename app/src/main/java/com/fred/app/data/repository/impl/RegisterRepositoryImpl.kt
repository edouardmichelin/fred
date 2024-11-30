package com.fred.app.data.repository.impl

import com.fred.app.data.datasource.base.RegisterDataSource
import com.fred.app.data.repository.base.RegisterRepository
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.util.State
import javax.inject.Inject

class RegisterRepositoryImpl
@Inject
constructor(
    private val registerDataSource: RegisterDataSource,
) : RegisterRepository {

  override suspend fun register(
      userId: String,
      username: String?,
      name: String?,
      mail: String?,
      address: Location?,
      gender: Gender?,
  ): State<User> {
    return try {
      when (val response =
          registerDataSource.register(
              userId = userId,
              username = username,
              name = name,
              mail = mail,
              address = address,
              gender = gender)) {
        is State.Success -> State.Success(response.data)
        is State.Error -> response
      }
    } catch (e: Exception) {
      State.Error(e)
    }
  }
}
