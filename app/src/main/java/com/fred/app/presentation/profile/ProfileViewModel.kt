package com.fred.app.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fred.app.base.BaseViewModel
import com.fred.app.base.IViewEvent
import com.fred.app.base.IViewState
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.domain.usecase.GetUserUseCase
import com.fred.app.domain.usecase.RegisterUserUseCase
import com.fred.app.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val getUserUseCase: GetUserUseCase,
    private val registerUseCase: RegisterUserUseCase,
) : ViewModel() {

  init {
    getUser()
  }

  private fun getUser() {

  }

}

/* todo
https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#6
 */
