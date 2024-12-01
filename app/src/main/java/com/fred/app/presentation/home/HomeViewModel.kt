package com.fred.app.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fred.app.data.repository.model.Suggestion
import com.fred.app.data.repository.model.User
import com.fred.app.domain.usecase.GetSuggestionsUseCase
import com.fred.app.domain.usecase.GetUserUseCase
import com.fred.app.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _suggestions = MutableStateFlow<List<Suggestion>>(emptyList())
    val suggestions: StateFlow<List<Suggestion>> = _suggestions

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    init {
        viewModelScope.launch {
            getUserUseCase().collect {
                when (it) {
                    is State.Success -> {
                        _user.value = it.data
                        getSuggestions()
                    }

                    is State.Error -> _user.value = null
                    is State.Loading -> _isLoading.value = true
                }
            }
        }
    }

    private fun getSuggestions() {
        if (_user.value == null) return

        viewModelScope.launch {
            getSuggestionsUseCase(_user.value!!).collect {
                when (it) {
                    is State.Loading -> _isLoading.value = true
                    is State.Error -> {
                        _suggestions.value = emptyList()
                        Log.d("SuggestionRepositoryImpl", "Error: ${it.exception}")
                    }
                    is State.Success -> {
                        Log.d("SuggestionRepositoryImpl", "Suggestions: ${it.data}")
                        _suggestions.value = it.data
                        _isLoading.value = false
                    }
                }
            }
        }
    }


}
