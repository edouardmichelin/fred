package com.fred.app.data.datasource.impl

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.fred.app.data.datasource.base.LoginDataSource
import com.fred.app.util.State
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor() : LoginDataSource {

    override suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser> {
        return try {
            val firebaseAuthInstance = FirebaseAuth.getInstance()
            firebaseAuthInstance.signInWithCredential(authCredential).await()
            //return firebaseAuthInstance.currentUser ?: throw FirebaseAuthException("", "")
            State.Success(firebaseAuthInstance.currentUser!!)
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}