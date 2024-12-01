package com.fred.app.data.repository.impl

import com.fred.app.data.repository.base.UserRepository
import com.fred.app.data.repository.model.User
import com.fred.app.util.State
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import com.fred.app.util.Constants.Firestore.USERS
import com.google.firebase.firestore.FieldValue
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
    private val db: FirebaseFirestore,
) : UserRepository {
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

  override suspend fun updateScoreBy(userId: String, score: Int): Flow<State<User>> = flow {
    emit(State.Loading)

    try {
      collection
        .document(userId)
        .update(User::score.name, FieldValue.increment(score.toLong()))
        .await()
      val ref = collection.document(userId).get().await()
      val data = ref.toObject(User::class.java)

      if (data != null) emit(State.Success(data))
      else emit(State.Error(Exception("Could not find user")))
    } catch (exception: Exception) {
      emit(State.Error(exception))
    }
  }
}
