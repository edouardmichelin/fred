package com.fred.app.presentation.onboarding.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fred.app.data.repository.model.Diet
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location
import com.fred.app.data.repository.model.User
import com.fred.app.domain.usecase.RegisterUserUseCase
import com.fred.app.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor (
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {
    private val _currUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currUser

    fun registerUser(
        username: String,
        name: String,
        mail: String,
        avatarId: Int,
        gender: Gender,
        address: Location,
        diet: Diet
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
            ).collect {
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
