package com.fred.app.data.repository.impl

import com.fred.app.data.repository.base.LocationRepository
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.LocationType
import com.fred.app.util.Constants.Firestore.LOCATIONS
import com.fred.app.util.State
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): LocationRepository {
    private val collection = db.collection(LOCATIONS)

    override suspend fun createLocation(
        name: String,
        latitude: Double,
        longitude: Double,
        ownerId: String,
        locationType: LocationType
    ): Flow<State<Location>> = flow {
        emit(State.Loading)

        try {
            val id = collection.document().id
            val location = Location(
                id = id,
                name = name,
                latitude = latitude,
                longitude = longitude,
                ownerId = ownerId,
                locationType = locationType
            )

            collection.document(id).set(location).await()
            val chatRef = collection.document(id).get().await()

            val data = chatRef.toObject(Location::class.java)

            if (data != null) emit(State.Success(data))
            else State.Error(Exception("Could not find location"))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    override suspend fun getAllLocationsOf(userId: String): Flow<State<List<Location>>> = flow {
        emit(State.Loading)

        try {
            val refs = collection
                .whereEqualTo(Location::ownerId.name, userId)
                .get()
                .await()
            val data = refs.toObjects(Location::class.java)

            if (data.isNotEmpty()) emit(State.Success(data))
            else State.Success(listOf<Location>())
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }
}