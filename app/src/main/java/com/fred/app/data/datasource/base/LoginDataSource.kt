package com.fred.app.data.datasource.base

import com.fred.app.util.State
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface LoginDataSource {

  suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser>
}
