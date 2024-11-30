package com.fred.app.data.datasource.impl

import com.fred.app.data.datasource.base.RegisterDataSource
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.util.Constants.Firestore.USERS
import com.fred.app.util.State
import com.fred.app.util.UserNotFoundException
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class RegisterDataSourceImpl
@Inject
constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : RegisterDataSource {

  override suspend fun register(
      userId: String,
      username: String?,
      name: String?,
      mail: String?,
      address: Location?,
      gender: Gender?,
  ): State<User> {
    return try {
      val user =
          User(
              username = username!!,
              name = name!!,
              mail = mail!!,
              address = address,
              gender = gender!!,
          )
      firebaseFirestore.collection(USERS).document(userId).set(user).await()
      val userRef = firebaseFirestore.collection(USERS).document(userId).get().await()

      val data = userRef.toObject(User::class.java)
      if (data != null) State.Success(data)
      else State.Error(UserNotFoundException("User not found!"))
    } catch (exception: Exception) {
      State.Error(exception)
    }
  }
}
