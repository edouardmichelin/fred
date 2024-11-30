package com.fred.app.data.repository.impl

import com.fred.app.data.datasource.base.ChatDataSource
import com.fred.app.data.repository.base.ChatRepository
import com.fred.app.data.repository.model.Chat
import com.fred.app.data.repository.model.mapModel
import com.fred.app.util.State
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDatasource: ChatDataSource,
) : ChatRepository {

    override suspend fun createChat(
        title: String,
        description: String,
        userId: String,
        date: Long,
    ): State<Chat> {
        return try {
            val random = (0..10000).random()
            val id = userId + random
            when (val response = chatDatasource.createChat(
                id = id,
                title = title,
                description = description,
                userId = userId,
                date = date
            )) {
                is State.Success -> State.Success(response.data.mapModel())
                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }

    override suspend fun getChat(): State<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllChats(): State<List<Chat>> {
        return try {
            when (val response = chatDatasource.getAllChats()) {
                is State.Success -> State.Success(response.data.map { it.mapModel() })
                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}