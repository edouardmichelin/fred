package com.fred.app.presentation.login

import android.view.View
import androidx.lifecycle.viewModelScope
import com.fred.app.base.BaseViewModel
import com.fred.app.base.IViewEvent
import com.fred.app.base.IViewState
import com.fred.app.domain.usecase.GetUserUseCase
import com.fred.app.domain.usecase.LoginUseCase
import com.fred.app.util.State
import com.fred.app.util.login.AuthenticationState
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val loginUseCase: LoginUseCase,
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel<LoginViewModel.ViewState, LoginViewModel.ViewEvent>() {

  fun loginWithCredential(authCredential: AuthCredential) {
    setState { state.copy(isLoading = true) }
    viewModelScope.launch {
      when (loginUseCase.execute(LoginUseCase.Input(authCredential = authCredential))) {
        is State.Success -> {
          getUserUseCase().collect {
            when (it) {
              is State.Success -> {
                triggerEvent(ViewEvent.SetUserRegistered(true))
                triggerEvent(ViewEvent.SetState(AuthenticationState.AUTHENTICATED))
              }
              is State.Error -> {
                triggerEvent(ViewEvent.SetUserRegistered(false))
                triggerEvent(ViewEvent.SetState(AuthenticationState.AUTHENTICATED))
              }
              is State.Loading -> {}
            }
          }
        }
        is State.Error -> {
          triggerEvent(ViewEvent.SetState(AuthenticationState.UNAUTHENTICATED))
        }
        is State.Loading -> {}
      }
    }
  }

  override fun createInitialState(): ViewState = ViewState()

  override fun triggerEvent(event: ViewEvent) {
    viewModelScope.launch {
      when (event) {
        is ViewEvent.SetState -> {
          setState { state.copy(isLoading = false, loginState = event.state) }
        }
        is ViewEvent.SetLoading -> {
          setState { state.copy(isLoading = event.state) }
        }
        is ViewEvent.SetUserRegistered -> {
          setState { state.copy(isUserRegistered = event.state)}
        }
      }
    }
  }

  sealed class ViewEvent : IViewEvent {
    class SetLoading(val state: Boolean) : ViewEvent()
    class SetState(val state: AuthenticationState) : ViewEvent()
    class SetUserRegistered(val state: Boolean): ViewEvent()
  }

  data class ViewState(
      val isLoading: Boolean = false,
      val loginState: AuthenticationState? = null,
      val isUserRegistered: Boolean = false
  ) : IViewState
}
