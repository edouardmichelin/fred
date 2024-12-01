package com.fred.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fred.app.data.repository.model.ActivityType
import com.fred.app.data.repository.model.Suggestion
import com.fred.app.domain.usecase.CreateActivityUseCase
import com.fred.app.domain.usecase.GetSuggestionsUseCase
import com.fred.app.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val createActivityUseCase: CreateActivityUseCase,
) : ViewModel() {
  private val _suggestions = MutableStateFlow<List<Suggestion>>(emptyList())
  val suggestions: StateFlow<List<Suggestion>> = _suggestions

  init {
   viewModelScope.launch {
       createActivityUseCase(
           type = ActivityType.Travel,
           distance = 10.0f,
           timestamp = System.currentTimeMillis(),
           vehicleId = "1",
           impact = 100
       )
       createActivityUseCase(
           type = ActivityType.Travel,
           distance = 15.0f,
           timestamp = System.currentTimeMillis(),
           vehicleId = "1",
           impact = 12
       )
   }
   getSuggestions()
  }

  private fun getSuggestions() {
    viewModelScope.launch {
        getSuggestionsUseCase().collect {
            when (it) {
            is State.Loading -> {}
            is State.Error -> _suggestions.value = emptyList()
            is State.Success -> _suggestions.value = it.data
          }
        }
    }
  }


}
