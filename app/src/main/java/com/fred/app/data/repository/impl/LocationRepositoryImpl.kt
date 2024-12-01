package com.fred.app.data.repository.impl

import android.os.NetworkOnMainThreadException
import android.util.Log
import com.fred.app.data.repository.base.LocationRepository
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.LocationType
import com.fred.app.util.Constants.Firestore.LOCATIONS
import com.fred.app.util.State
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.IOException
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val client: OkHttpClient
): LocationRepository {
    private val collection = db.collection(LOCATIONS)

    override suspend fun createLocation(
        name: String,
        latitude: Double,
        longitude: Double,
        ownerId: String,
        locationType: LocationType,
        country: String
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
                locationType = locationType,
                country = country
            )

            collection.document(id).set(location).await()
            val chatRef = collection.document(id).get().await()

            val data = chatRef.toObject(Location::class.java)

            if (data != null) emit(State.Success(data))
            else emit(State.Error(Exception("Could not find location")))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    override suspend fun search(query: String): Flow<State<List<Location>>> = flow {
        emit(State.Loading)

        val url = HttpUrl.Builder()
            .scheme("https")
            .host("nominatim.openstreetmap.org")
            .addPathSegment("search")
            .addQueryParameter("q", query)
            .addQueryParameter("addressdetails", "1")
            .addQueryParameter("format", "json")
            .build()

        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "Fred/1.0 (app@fred.com)") // Set a proper User-Agent
            .header("Referer", "https://fred.com") // Optionally add a Referer
            .build()

        try {
            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }

            response.use {
                if (!response.isSuccessful) {
                    throw Exception("Unexpected code $response")
                }

                val body = response.body?.string()
                if (body != null) {
                    emit(State.Success(parseBody(body)))
                } else {
                    emit(State.Success(emptyList()))
                }
            }
        } catch (e: Exception) {
            emit(State.Error(e))
        }
    }

    override suspend fun getAllLocationsOf(userId: String): Flow<State<List<Location>>> = flow {
        emit(State.Loading)
        Log.d("getAllLocationsOf", Location::ownerId.name)
        try {
            val refs = collection
                .whereEqualTo(Location::ownerId.name, userId)
                .get()
                .await()
            val data = refs.toObjects(Location::class.java)

            if (data.isNotEmpty()) emit(State.Success(data))
            else emit(State.Success(listOf()))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    private fun parseBody(body: String): List<Location> {
        val jsonArray = JSONArray(body)

        return List(jsonArray.length()) { i ->
            val jsonObject = jsonArray.getJSONObject(i)
            val lat = jsonObject.getDouble("lat")
            val lon = jsonObject.getDouble("lon")
            val name = jsonObject.getString("display_name")
            val country = jsonObject.getJSONObject("address").getString("country_code")
            Location(latitude = lat, longitude = lon, name = name, country = country.uppercase())
        }
    }
}