package com.fred.app.presentation.onboarding.transports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fred.app.data.repository.model.FuelType
import com.fred.app.data.repository.model.User
import com.fred.app.data.repository.model.VehicleType
import com.fred.app.domain.usecase.CreateVehicleUseCase
import com.fred.app.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransportsViewModel @Inject constructor (
    private val registerVehicleUseCase: CreateVehicleUseCase,
) : ViewModel()
{
    private val _currUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currUser

    fun createVehicle(
        type: VehicleType,
        name: String,
        fuelType: FuelType,
        age: Int,
        km: Int,
        carbonFootprint: Double,
    ) {
        viewModelScope.launch {
            registerVehicleUseCase (
                type = type,
                name = name,
                fuelType = fuelType,
                age = age,
                km = km,
                carbonFootprint = carbonFootprint,
            ).collect {
                when (it) {
                    is State.Loading -> {}
                    is State.Error -> {
                        _currUser.value = null
                        /** HANDLE ERROR */
                    }
                    is State.Success -> {
                        _currUser.value
                    }
                }
            }
        }
    }



}
