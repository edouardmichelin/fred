package com.fred.app.data.repository.impl

import com.fred.app.data.repository.base.LocationRepository
import com.fred.app.data.repository.base.VehicleRepository
import com.fred.app.data.repository.model.Activity
import com.fred.app.data.repository.model.FuelType
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.Vehicle
import com.fred.app.data.repository.model.VehicleType
import com.fred.app.util.Constants.Firestore.VEHICLES
import com.fred.app.util.State
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): VehicleRepository {
    private val collection = db.collection(VEHICLES)

    override suspend fun createVehicle(
        type: VehicleType,
        name: String,
        fuelType: FuelType,
        age: Int,
        km: Int,
        carbonFootprint: Double,
        ownerId: String,
    ): Flow<State<Vehicle>> = flow {
        emit(State.Loading)

        try {
            val id = collection.document().id
            val vehicle = Vehicle(
                id = id,
            )

            collection.document(id).set(vehicle).await()
            val chatRef = collection.document(id).get().await()

            val data = chatRef.toObject(Vehicle::class.java)

            if (data != null) emit(State.Success(data))
            else State.Error(Exception("Could not find vehicle"))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    override suspend fun getAllVehicles(): Flow<State<List<Vehicle>>> = flow {
        emit(State.Loading)

        try {
            val refs = collection.get().await()
            val data = refs.toObjects(Vehicle::class.java)

            if (data.isNotEmpty()) emit(State.Success(data))
            else State.Success(listOf<Vehicle>())
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }
}