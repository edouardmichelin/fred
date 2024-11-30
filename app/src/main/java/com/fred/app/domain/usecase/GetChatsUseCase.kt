package com.fred.app.domain.usecase

import com.fred.app.base.UseCase
import com.fred.app.data.repository.base.ChatRepository
import com.fred.app.data.repository.model.Chat
import com.fred.app.util.State
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
) : UseCase<Nothing, List<Chat>>() {

    override suspend fun invoke(input: Nothing?): State<List<Chat>> {
        return try {
            when (val response = chatRepository.getAllChats()
            ) {
                is State.Success -> response
                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}