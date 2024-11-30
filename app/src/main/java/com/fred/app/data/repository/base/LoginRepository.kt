package com.fred.app.data.repository.base

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.fred.app.util.State

interface LoginRepository {

    suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser>
}