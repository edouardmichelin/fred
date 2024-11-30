package com.fred.app.presentation.register

import androidx.lifecycle.viewModelScope
import com.fred.app.base.BaseViewModel
import com.fred.app.base.IViewEvent
import com.fred.app.base.IViewState
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.domain.usecase.RegisterUserUseCase
import com.fred.app.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private val registerUseCase: RegisterUserUseCase,
) : BaseViewModel<RegisterViewModel.ViewState, RegisterViewModel.ViewEvent>() {

  fun temp() {
    viewModelScope.launch {
    }
  }

  override fun createInitialState(): ViewState = ViewState()

  override fun triggerEvent(event: ViewEvent) {
    viewModelScope.launch {
      when (event) {
        is ViewEvent.Event -> {}
        is ViewEvent.SetAddress -> {
          setState { state.copy(address = event.text) }
        }
        is ViewEvent.SetEmail -> {
          setState { state.copy(email = event.text) }
        }
        is ViewEvent.SetUsername -> {
          setState { state.copy(username = event.text) }
        }
        is ViewEvent.SetName -> {
          setState { state.copy(name = event.text) }
        }
      }
    }
  }

  sealed class ViewEvent : IViewEvent {
    class SetUsername(val text: String) : ViewEvent()

    class SetName(val text: String) : ViewEvent()

    class SetEmail(val text: String) : ViewEvent()

    class SetAddress(val text: Location) : ViewEvent()

    object Event : ViewEvent()
  }

  data class ViewState(
      val username: String = "",
      val name: String = "",
      val email: String = "",
      val address: Location? = null,
      val isDisplay: Boolean = false,
      val isLoading: Boolean = false,
  ) : IViewState
}
