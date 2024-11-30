package com.fred.app.presentation.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.fred.app.base.BaseViewModel
import com.fred.app.base.IViewEvent
import com.fred.app.base.IViewState
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.domain.usecase.GetUserUseCase
import com.fred.app.domain.usecase.RegisterUseCase
import com.fred.app.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val getUserUseCase: GetUserUseCase,
    private val registerUseCase: RegisterUseCase,
) : BaseViewModel<ProfileViewModel.ViewState, ProfileViewModel.ViewEvent>() {

  override fun createInitialState(): ViewState = ViewState()

  init {
    getUser()
  }

  private fun getUser() {
    triggerEvent(ViewEvent.SetLoading(true))
    viewModelScope.launch {
      when (val result = getUserUseCase.execute(input = null)) {
        is State.Success -> {
          val user = result.data
          triggerEvent(ViewEvent.SetUser(user))
        }
        is State.Error -> {
          Log.d("TAG", "${result.exception}")
          result.exception.message?.let { triggerEvent(ViewEvent.SetGetUserError(it)) }
        }
          is State.Loading -> {}
      }
    }
  }

  private fun updateUser() {
    triggerEvent(ViewEvent.SetLoading(true))
    viewModelScope.launch {
      when (val response =
          registerUseCase.execute(
              RegisterUseCase.Input(
                  username = state.username,
                  name = state.name,
                  mail = state.email,
                  address = state.address,
                  gender = Gender.Other,
              ))) {
        is State.Success -> {
          triggerEvent(ViewEvent.SetUser(response.data))
        }
        is State.Error -> {
          response.exception.message?.let { triggerEvent(ViewEvent.SetGetUserError(it)) }
        }
        is State.Loading -> {}
      }
    }
  }

  override fun triggerEvent(event: ViewEvent) {
    viewModelScope.launch {
      when (event) {
        ViewEvent.Event -> {
          getUser()
        }
        is ViewEvent.SetGetUserError -> {
          setState { state.copy(isLoading = false, getUserError = event.value) }
        }
        is ViewEvent.SetLoading -> {
          setState { state.copy(isLoading = event.status) }
        }
        is ViewEvent.SetUser -> {
          setState {
            state.copy(
                isLoading = false,
                username = event.user.username ?: "",
                name = event.user.name ?: "",
                email = "",
                address = event.user.address,
                gender = event.user.gender ?: Gender.Other)
          }
        }
        is ViewEvent.SetName -> {
          setState { state.copy(name = event.name) }
        }
        is ViewEvent.SetAddress -> {
          setState { state.copy(address = event.address) }
        }
        is ViewEvent.SetEmail -> {
          setState { state.copy(email = event.email) }
        }
        is ViewEvent.SetUsername -> {
          setState { state.copy(username = event.username) }
        }
        ViewEvent.ApplyClick -> {
          setState { state.copy(editMode = false) }
          updateUser()
        }
        ViewEvent.EditClick -> {
          setState { state.copy(editMode = true) }
        }

      }
    }
  }

  sealed class ViewEvent : IViewEvent {
    object Event : ViewEvent()

    class SetGetUserError(val value: String) : ViewEvent()

    class SetLoading(val status: Boolean) : ViewEvent()

    class SetUser(val user: User) : ViewEvent()

    class SetName(val name: String) : ViewEvent()

    class SetUsername(val username: String) : ViewEvent()

    class SetEmail(val email: String) : ViewEvent()

    class SetAddress(val address: Location?) : ViewEvent()

    object EditClick : ViewEvent()

    object ApplyClick : ViewEvent()
  }

  data class ViewState(
      val isLoading: Boolean = false,
      val getUserError: String = "",
      val username: String = "",
      val name: String = "",
      val email: String = "",
      val address: Location? = null,
      val termsCheck: Boolean = false,
      val newsletterCheck: Boolean = false,
      val gender: Gender = Gender.Other,
      val editMode: Boolean = false,
  ) : IViewState
}

/* todo
https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#6
 */
