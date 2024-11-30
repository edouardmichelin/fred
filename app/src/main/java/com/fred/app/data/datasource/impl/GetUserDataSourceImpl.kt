package com.fred.app.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.fred.app.data.datasource.base.GetUserDataSource
import com.fred.app.data.datasource.entity.UserDTO
import com.fred.app.util.Constants.Firestore.USERS
import com.fred.app.util.State
import com.fred.app.util.UserNotFoundException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetUserDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : GetUserDataSource {

    override suspend fun getUserById(userId: String): State<UserDTO> {
        return try {
            val userRef = firebaseFirestore.collection(USERS).document(userId).get().await()
            val data = userRef.toObject(UserDTO::class.java)
            if (data != null)
                State.Success(data)
            else
                State.Error(UserNotFoundException("User not found!"))

        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}