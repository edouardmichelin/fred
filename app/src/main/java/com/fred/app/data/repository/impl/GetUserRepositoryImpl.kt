package com.fred.app.data.repository.impl

import com.fred.app.data.datasource.base.GetUserDataSource
import com.fred.app.data.repository.base.GetUserRepository
import com.fred.app.data.repository.model.User
import com.fred.app.data.repository.model.mapModel
import com.fred.app.util.State
import javax.inject.Inject

class GetUserRepositoryImpl @Inject constructor(
    private val getUserDataSource: GetUserDataSource,
) : GetUserRepository {

    override suspend fun getUserById(userId: String): State<User> {
        return try {
            when (val response = getUserDataSource.getUserById(userId = userId)) {
                is State.Success -> State.Success(response.data.mapModel())
                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}