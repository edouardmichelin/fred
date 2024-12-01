package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.RegisterUserRepository
import com.fred.app.data.repository.model.Diet
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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
      avatarId: String,
      gender: Gender,
      address: Location,
      diet: Diet
  ): Flow<State<User>> {
    val factor_age = if (age < 18) 2 else if (age < 65) 3 else 2
    val factor_diet =
        if (diet == Diet.Vegan) 1
        else if (diet == Diet.Vegetarian) 2 else if (diet == Diet.Omnivore) 3 else 4
    val factor_gender = if (gender == Gender.Male) 1.2 else 0.8
    val score = 3000 + 1000 * factor_age + 1000 * factor_diet + 1000 * factor_gender
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
          score = score.toInt())
    } ?: run { flowOf(State.Error(Exception(""))) }
  }
}
