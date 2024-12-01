package com.fred.app.data.repository.impl

import com.fred.app.data.repository.base.RegisterUserRepository
import com.fred.app.data.repository.model.Diet
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.data.repository.model.Vehicle
import com.fred.app.util.Constants.Firestore.USERS
import com.fred.app.util.State
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterUserRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
) : RegisterUserRepository {
    private val collection = db.collection(USERS)

  override suspend fun register(
      id: String,
      username: String,
      name: String,
      mail: String,
      avatarId: Int,
      gender: Gender,
      address: Location,
      diet: Diet,
      transportations: List<Vehicle>,
      locations: List<Location>,
      score: Int
  ): Flow<State<User>> = flow {
      emit(State.Loading)

      try {
          val user = User(
              id = id,
              username = username,
              name = name,
              mail = mail,
              avatarId = avatarId,
              gender = gender,
              address = address,
              diet = diet,
              transportations = transportations,
              locations = locations,
              score = score
          )

          collection.document(id).set(user).await()
          val chatRef = collection.document(id).get().await()

          val data = chatRef.toObject(User::class.java)

          if (data != null) emit(State.Success(data))
          else State.Error(Exception("Could not find user"))
      } catch (exception: Exception) {
          emit(State.Error(exception))
      }
  }
}
