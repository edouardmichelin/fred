package com.fred.app.domain.usecase

import com.fred.app.base.Inputs
import com.fred.app.base.UseCase
import com.fred.app.data.repository.base.RegisterRepository
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import com.fred.app.util.UserNotFoundException
import javax.inject.Inject

class RegisterUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val registerRepository: RegisterRepository,
) : UseCase<RegisterUseCase.Input, User>() {

  override suspend fun invoke(input: Input?): State<User> {
    return try {
      authService.userId?.let {
        when (val response =
            registerRepository.register(
                userId = it,
                username = input?.username,
                name = input?.name,
                mail = input?.mail,
                address = input?.address,
                gender = input?.gender)) {
          is State.Success -> response
          is State.Error -> response
        }
      } ?: run { State.Error(UserNotFoundException("User not found!")) }
    } catch (exception: Exception) {
      State.Error(exception)
    }
  }

  data class Input(
      val username: String? = null,
      val name: String? = null,
      val mail: String? = null,
      val address: Location? = null,
      val gender: Gender? = null,
  ) : Inputs
}
