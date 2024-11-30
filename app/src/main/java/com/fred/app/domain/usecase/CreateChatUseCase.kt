package com.fred.app.domain.usecase

import com.fred.app.base.Inputs
import com.fred.app.base.UseCase
import com.fred.app.data.repository.base.ChatRepository
import com.fred.app.data.repository.model.Chat
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import com.fred.app.util.UserNotFoundException
import javax.inject.Inject

class CreateChatUseCase @Inject constructor(
    private val authService: AuthService,
    private val chatRepository: ChatRepository,
) : UseCase<CreateChatUseCase.Input, Chat>() {

    override suspend fun invoke(input: Input?): State<Chat> {
        return try {
            authService.userId?.let {
                when (val response = chatRepository.createChat(
                    title = input?.title ?: "",
                    description = input?.description ?: "",
                    userId = it,
                    date = System.currentTimeMillis()
                )) {
                    is State.Success -> response
                    is State.Error -> response
                }
            } ?: run {
                State.Error(UserNotFoundException("User not found!"))
            }

        } catch (exception: Exception) {
            State.Error(exception)
        }
    }

    data class Input(
        val title: String? = null,
        val description: String? = null,
        val isAnonymous: Boolean = false,
    ) : Inputs
}