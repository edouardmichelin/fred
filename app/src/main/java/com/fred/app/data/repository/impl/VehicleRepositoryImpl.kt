package com.fred.app.data.repository.impl

import com.fred.app.data.repository.base.VehicleRepository
import com.fred.app.data.repository.model.FuelType
import com.fred.app.data.repository.model.Vehicle
import com.fred.app.data.repository.model.VehicleType
import com.fred.app.util.Constants.Firestore.VEHICLES
import com.fred.app.util.State
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class VehicleRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    VehicleRepository {
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
      val vehicle =
          Vehicle(
              id = id,
              type = type,
              name = name,
              fuelType = fuelType,
              age = age,
              km = km,
              carbonFootprint = carbonFootprint,
              ownerId = ownerId)

      collection.document(id).set(vehicle).await()
      val ref = collection.document(id).get().await()

      val data = ref.toObject(Vehicle::class.java)

      if (data != null) emit(State.Success(data))
      else emit(State.Error(Exception("Could not find vehicle")))
    } catch (exception: Exception) {
      emit(State.Error(exception))
    }
  }

  override suspend fun getVehicleById(vehicleId: String): Flow<State<Vehicle>> = flow {
    emit(State.Loading)

    try {
      val refs = collection.document(vehicleId).get().await()
      val data = refs.toObject(Vehicle::class.java)

      if (data != null) emit(State.Success(data))
      else emit(State.Error(Exception("Could not find vehicle")))
    } catch (exception: Exception) {
      emit(State.Error(exception))
    }
  }

  override suspend fun getAllVehiclesOf(userId: String): Flow<State<List<Vehicle>>> = flow {
    emit(State.Loading)

    try {
      val refs = collection.whereEqualTo(Vehicle::ownerId.name, userId).get().await()
      val data = refs.toObjects(Vehicle::class.java)

      if (data.isNotEmpty()) emit(State.Success(data)) else emit(State.Success(listOf()))
    } catch (exception: Exception) {
      emit(State.Error(exception))
    }
  }
}
