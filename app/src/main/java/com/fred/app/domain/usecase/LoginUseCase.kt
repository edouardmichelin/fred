package com.fred.app.domain.usecase

import com.fred.app.base.Inputs
import com.fred.app.base.UseCase
import com.fred.app.data.repository.base.LoginRepository
import com.fred.app.util.State
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

class LoginUseCase
constructor(
    private val loginRepository: LoginRepository,
) : UseCase<LoginUseCase.Input?, FirebaseUser>() {

  override suspend fun invoke(input: Input?): State<FirebaseUser> {
    return try {
      when (val response = loginRepository.loginWithCredential(input!!.authCredential)) {
        is State.Success -> response
        is State.Error -> response
        is State.Loading -> State.Loading
      }
    } catch (e: Exception) {
      State.Error(e)
    }
  }

  data class Input(
      val authCredential: AuthCredential,
  ) : Inputs
}

/*
override suspend fun invoke(input: Input): Flow<FirebaseUser> = flow {
    try {
        when (val response = loginRepository.loginWithCredential(input.authCredential)) {
            is State.Success -> emit(response.data)

            is State.Error -> response
        }
    } catch (e: Exception) {
        State.Error(e)
    }
}
*/
