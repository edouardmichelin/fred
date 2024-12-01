package com.fred.app.data.repository.impl

import com.fred.app.data.datasource.base.GetUserDataSource
import com.fred.app.data.repository.base.GetUserRepository
import com.fred.app.data.repository.model.Activity
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.util.State
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import com.fred.app.util.Constants.Firestore.USERS
import javax.inject.Inject

class GetUserRepositoryImpl
@Inject
constructor(
    private val db: FirebaseFirestore,
) : GetUserRepository {
  private val collection = db.collection(USERS)

  override suspend fun getUserById(userId: String): Flow<State<User>> = flow {
    emit(State.Loading)

    try {
      val refs = collection
        .document(userId)
        .get()
        .await()
      val data = refs.toObject(User::class.java)

      if (data != null) emit(State.Success(data))
      else emit(State.Error(Exception("Could not find user")))
    } catch (exception: Exception) {
      emit(State.Error(exception))
    }
  }
}
