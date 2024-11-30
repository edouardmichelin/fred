package com.fred.app.data.repository.base

import com.fred.app.util.State
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface LoginRepository {

  suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser>
}
