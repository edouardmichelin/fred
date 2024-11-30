package com.fred.app.data.datasource.impl

import com.fred.app.data.datasource.base.LoginDataSource
import com.fred.app.util.State
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class LoginDataSourceImpl @Inject constructor() : LoginDataSource {

  override suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser> {
    return try {
      val firebaseAuthInstance = FirebaseAuth.getInstance()
      firebaseAuthInstance.signInWithCredential(authCredential).await()
      // return firebaseAuthInstance.currentUser ?: throw FirebaseAuthException("", "")
      State.Success(firebaseAuthInstance.currentUser!!)
    } catch (exception: Exception) {
      State.Error(exception)
    }
  }
}
