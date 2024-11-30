package com.fred.app.domain.usecase

import com.fred.app.base.UseCase
import com.fred.app.data.repository.base.GetUserRepository
import com.fred.app.data.repository.model.User
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.CommonException
import com.fred.app.util.State
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val authService: AuthService,
    private val getUserRepository: GetUserRepository,
) : UseCase<Nothing, User>() {

    override suspend fun invoke(input: Nothing?): State<User> {
        return try {
            authService.userId?.let {
                when (val response = getUserRepository.getUserById(it)) {
                    is State.Success -> response
                    is State.Error -> response
                }
            } ?: run {
                State.Error(CommonException())
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}