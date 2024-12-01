package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.RegisterUserRepository
import com.fred.app.data.repository.model.Diet
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RegisterUserUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val registerUserRepository: RegisterUserRepository,
) {
  suspend operator fun invoke(
      username: String,
      age: Int,
      name: String,
      mail: String,
      avatarId: Int,
      gender: Gender,
      address: Location,
      diet: Diet
  ): Flow<State<User>> {
    val score = 0
    return authService.userId?.let {
        registerUserRepository.register(
                id = it,
                username = username,
                age = age,
                name = name,
                mail = mail,
                avatarId = avatarId,
                gender = gender,
                address = address,
                diet = diet,
                transportations = listOf(),
                locations = listOf(),
                score = score
            )
        } ?: run { flowOf(State.Error(Exception(""))) }
  }
}
