package com.fred.app.presentation.register

import androidx.lifecycle.viewModelScope
import com.fred.app.base.BaseViewModel
import com.fred.app.base.IViewEvent
import com.fred.app.base.IViewState
import com.fred.app.domain.usecase.RegisterUseCase
import com.fred.app.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private val registerUseCase: RegisterUseCase,
) : BaseViewModel<RegisterViewModel.ViewState, RegisterViewModel.ViewEvent>() {

  fun temp() {
    viewModelScope.launch {
      when (val response =
          registerUseCase.execute(
              RegisterUseCase.Input(
                  username = state.username,
                  name = state.name,
                  phone = state.phone,
                  mail = state.email,
                  address = state.address,
                  gender = false,
              ))) {
        is State.Success -> {
          response.data
        }
        is State.Error -> {
          response.exception
        }
      }
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
        is ViewEvent.SetPhone -> {
          setState { state.copy(phone = event.text) }
        }
        is ViewEvent.SetNewsletterCheck -> {
          setState { state.copy(newsletterCheck = event.status) }
        }
        is ViewEvent.SetTermsCheck -> {
          setState { state.copy(termsCheck = event.status) }
        }
      }
    }
  }

  sealed class ViewEvent : IViewEvent {
    class SetUsername(val text: String) : ViewEvent()

    class SetName(val text: String) : ViewEvent()

    class SetPhone(val text: String) : ViewEvent()

    class SetEmail(val text: String) : ViewEvent()

    class SetAddress(val text: String) : ViewEvent()

    class SetTermsCheck(val status: Boolean) : ViewEvent()

    class SetNewsletterCheck(val status: Boolean) : ViewEvent()

    object Event : ViewEvent()
  }

  data class ViewState(
      val username: String = "",
      val name: String = "",
      val phone: String = "",
      val email: String = "",
      val address: String = "",
      val termsCheck: Boolean = false,
      val newsletterCheck: Boolean = false,
      val isDisplay: Boolean = false,
      val isLoading: Boolean = false,
  ) : IViewState
}
