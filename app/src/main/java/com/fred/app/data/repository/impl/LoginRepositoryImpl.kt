package com.fred.app.data.repository.impl

import com.fred.app.data.datasource.base.LoginDataSource
import com.fred.app.data.repository.base.LoginRepository
import com.fred.app.util.State
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginRepositoryImpl
@Inject
constructor(
    private val loginDataSource: LoginDataSource,
) : LoginRepository {

  // TODO: Add mapper to success branch
  override suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser> {
    // return loginDataSource.loginWithCredential(authCredential)
    return try {
      when (val response = loginDataSource.loginWithCredential(authCredential)) {
        is State.Success -> response
        is State.Error -> response
      }
    } catch (e: Exception) {
      State.Error(e)
    }
  }
}
