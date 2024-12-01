package com.fred.app.presentation.onboarding.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fred.app.data.repository.model.Diet
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.domain.usecase.RegisterUserUseCase
import com.fred.app.domain.usecase.SearchLocationUseCase
import com.fred.app.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PersonalViewModel
@Inject
constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val searchLocationUseCase: SearchLocationUseCase
) : ViewModel() {
  private val _currUser = MutableStateFlow<User?>(null)
  val currentUser: StateFlow<User?> = _currUser

  private val _queriedLocations = MutableStateFlow<List<Location>>(emptyList())
  val queriedLocations: StateFlow<List<Location>> = _queriedLocations

  fun searchLocation(query: String) {
    if (query.isEmpty()) return

    viewModelScope.launch {
      searchLocationUseCase(query).collect {
        when (it) {
          is State.Loading -> {}
          is State.Error -> _queriedLocations.value = emptyList()
          is State.Success -> _queriedLocations.value = it.data
        }
      }
    }
  }

  fun registerUser(
      username: String,
      name: String,
      mail: String,
      avatarId: String,
      gender: Gender,
      address: Location,
      diet: Diet,
      age: Int,
  ) {
    viewModelScope.launch {
      registerUserUseCase(
              username = username,
              name = name,
              mail = mail,
              avatarId = avatarId,
              gender = gender,
              address = address,
              diet = diet,
              age = age,
          )
          .collect {
            when (it) {
              is State.Loading -> {}
              is State.Error -> {
                _currUser.value = null
                /** HANDLE ERROR */
              }
              is State.Success -> {
                _currUser.value = it.data
              }
            }
          }
    }
  }
}
