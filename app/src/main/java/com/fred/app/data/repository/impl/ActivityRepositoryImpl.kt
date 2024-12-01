package com.fred.app.data.repository.impl

import com.fred.app.data.repository.base.ActivityRepository
import com.fred.app.data.repository.model.Activity
import com.fred.app.data.repository.model.ActivityType
import com.fred.app.data.repository.model.Location
import com.fred.app.util.Constants.Firestore.ACTIVITIES
import com.fred.app.util.State
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): ActivityRepository {
    private val collection = db.collection(ACTIVITIES)

    override suspend fun createActivity(
        type: ActivityType,
        distance: Float,
        vehicleId: String,
        impact: Int,
        ownerId: String,
        description: String
    ): Flow<State<Activity>> = flow {
        emit(State.Loading)

        try {
            val id = collection.document().id
            val activity = Activity(
                id = id,
                type = type,
                distance = distance,
                vehicleId = vehicleId,
                impact = impact,
                ownerId = ownerId,
                description = description
            )

            collection.document(id).set(activity).await()
            val chatRef = collection.document(id).get().await()

            val data = chatRef.toObject(Activity::class.java)

            if (data != null) emit(State.Success(data))
            else emit(State.Error(Exception("Could not find activity")))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    override suspend fun getAllActivitiesOf(userId: String): Flow<State<List<Activity>>> = flow {
        emit(State.Loading)

        try {
            val refs = collection
                .whereEqualTo(Location::ownerId.name, userId)
                .get()
                .await()
            val data = refs.toObjects(Activity::class.java)

            if (data.isNotEmpty()) emit(State.Success(data))
            else emit(State.Success(listOf()))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }
}