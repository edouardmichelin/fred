package com.fred.app.data.repository.impl

import android.util.Log
import com.fred.app.data.repository.base.SuggestionRepository
import com.fred.app.data.repository.model.Activity
import com.fred.app.data.repository.model.Suggestion
import com.fred.app.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class SuggestionRepositoryImpl @Inject constructor(
    private val client: OkHttpClient,
    private val openAiApiKey: String = "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"
) : SuggestionRepository {
    override suspend fun get(prompt: String): Flow<State<List<Suggestion>>> = flow {
        emit(State.Loading)

        val url = "https://api.openai.com/v1/chat/completions"

        val jsonBody = JSONObject().apply {
            put("model", "gpt-3.5-turbo")
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", "You are a helpful assistant.  Do not add title. Do not add numbers. " +
                            "You should help the user to reduce their carbon footprint by proposing 3 actions they can do today!. Answer in three simples lines.")
                })
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", prompt)
                })
            })
            put("temperature", 0.7)
        }

        val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .header("Authorization", "Bearer $openAiApiKey")
            .header("Content-Type", "application/json")
            .build()

        try {
            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }

            response.use {
                if (!response.isSuccessful) {
                    throw Exception("OpenAI API call failed: ${response.code}")
                }

                val body = response.body?.string()
                if (body != null) {
                    emit(State.Success(parseOpenAiResponse(body)))
                } else {
                    emit(State.Success(emptyList()))
                }
            }
        } catch (e: Exception) {
            emit(State.Error(e))
        }
    }

    private fun parseOpenAiResponse(body: String): List<Suggestion> {
        val jsonObject = JSONObject(body)
        val choices = jsonObject.getJSONArray("choices")

        val suggestions = mutableListOf<Suggestion>()

        for (i in 0 until choices.length()) {
            val choice = choices.getJSONObject(i)
            val message = choice.getJSONObject("message")
            val content = message.getString("content")
                .replace("1.", "")
                .replace("2.", "")
                .replace("3.", "")


            val sug = content.split("\n")

            Log.d("SuggestionRepositoryImpl", "Suggestion: $content")

            for (s in sug) {
                suggestions.add(
                    Suggestion(
                        title = s.trim(), // Assuming each suggestion is a line of text
                        activity = Activity() // Replace with actual Activity parsing logic if needed
                    )
                )
            }
        }



        return suggestions
    }
}
