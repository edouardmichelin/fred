package com.fred.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fred.app.data.repository.model.User
import com.fred.app.domain.usecase.GetUserUseCase
import com.fred.app.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) :
    ViewModel() {
  private val _user = MutableStateFlow<User?>(null)
  val user: StateFlow<User?> = _user.asStateFlow()

  private val _isLoading = MutableStateFlow(false)
  val isLoading = _isLoading

  init {
    getUser()
  }

  private fun getUser() {
    viewModelScope.launch {
      getUserUseCase().collect {
        when (it) {
          is State.Success -> {
            _user.value = it.data
            _isLoading.value = false
          }
          is State.Error -> _user.value = null
          is State.Loading -> _isLoading.value = true
        }
      }
    }
  }
}

/* todo
https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#6
 */
