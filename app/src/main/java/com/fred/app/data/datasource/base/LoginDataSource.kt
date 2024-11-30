package com.fred.app.data.datasource.base

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.fred.app.util.State

interface LoginDataSource {

    suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser>
}