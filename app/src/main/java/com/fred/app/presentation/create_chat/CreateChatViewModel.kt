package com.fred.app.presentation.create_chat
/*
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.fred.app.base.BaseViewModel
import com.fred.app.base.IViewEvent
import com.fred.app.base.IViewState
import com.fred.app.data.repository.model.Chat
import com.fred.app.domain.usecase.CreateChatUseCase
import com.fred.app.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CreateChatViewModel
@Inject
constructor(
    private val createChatUseCase: CreateChatUseCase,
) : BaseViewModel<CreateChatViewModel.ViewState, CreateChatViewModel.ViewEvent>() {

  override fun createInitialState(): ViewState = ViewState()

  private fun createChat() {
    triggerEvent(ViewEvent.SetLoading(true))
    viewModelScope.launch {
      when (val response =
          createChatUseCase.execute(
              CreateChatUseCase.Input(title = state.title, description = state.description))) {
        is State.Success -> {
          triggerEvent(ViewEvent.SetCreatedChat(response.data))
        }
        is State.Error -> {
          Log.d("TAG", "${response.exception}")
          response.exception.message?.let { triggerEvent(ViewEvent.SetCreateChatError(it)) }
        }
      }
    }
  }

  override fun triggerEvent(event: ViewEvent) {
    viewModelScope.launch {
      when (event) {
        is ViewEvent.SetLoading -> {
          setState { state.copy(isLoading = event.value, createChatError = "") }
        }
        is ViewEvent.SetDescription -> {
          setState { state.copy(description = event.description) }
        }
        is ViewEvent.SetTitle -> {
          setState { state.copy(title = event.title) }
        }
        ViewEvent.CreateClicked -> {
          createChat()
        }
        is ViewEvent.SetCreatedChat -> {
          setState { state.copy(isLoading = false, createdChat = event.chat) }
        }
        is ViewEvent.SetCreateChatError -> {
          setState { state.copy(isLoading = false, createChatError = event.value) }
        }
      }
    }
  }

  sealed class ViewEvent : IViewEvent {
    class SetLoading(val value: Boolean) : ViewEvent()

    internal class SetCreateChatError(val value: String) : ViewEvent()

    class SetTitle(val title: String) : ViewEvent()

    class SetDescription(val description: String) : ViewEvent()

    object CreateClicked : ViewEvent()

    internal class SetCreatedChat(val chat: Chat) : ViewEvent()
  }

  data class ViewState(
      val isLoading: Boolean = false,
      val createChatError: String = "",
      val title: String = "",
      val description: String = "",
      val createdChat: Chat? = null,
  ) : IViewState
}

enum class Create {
  CREATED,
}


 */