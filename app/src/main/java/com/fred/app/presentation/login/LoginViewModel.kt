package com.fred.app.presentation.login

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.fred.app.base.BaseViewModel
import com.fred.app.base.IViewEvent
import com.fred.app.base.IViewState
import com.fred.app.domain.usecase.LoginUseCase
import com.fred.app.util.State
import com.fred.app.util.login.AuthenticationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginViewModel.ViewState, LoginViewModel.ViewEvent>() {

    fun loginWithCredential(authCredential: AuthCredential) {
        setState { state.copy(isLoading = true) }
        viewModelScope.launch {
            when (loginUseCase.execute(LoginUseCase.Input(authCredential = authCredential))) {
                is State.Success -> {
                    triggerEvent(ViewEvent.SetState(AuthenticationState.AUTHENTICATED))
                }
                is State.Error -> {
                    triggerEvent(ViewEvent.SetState(AuthenticationState.UNAUTHENTICATED))
                }
            }
        }
    }

    override fun createInitialState(): ViewState = ViewState()

    override fun triggerEvent(event: ViewEvent) {
        viewModelScope.launch {
            when (event) {
                is ViewEvent.SetState -> {
                    setState {
                        state.copy(
                            isLoading = false,
                            loginState = event.state
                        )
                    }
                }
                is ViewEvent.SetLoading -> {
                    setState {
                        state.copy(
                            isLoading = event.state
                        )
                    }
                }
            }
        }
    }

    sealed class ViewEvent : IViewEvent {
        class SetLoading(val state: Boolean) : ViewEvent()
        class SetState(val state: AuthenticationState) : ViewEvent()
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val loginState: AuthenticationState? = null,
    ) : IViewState
}