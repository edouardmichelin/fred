package com.fred.app.domain.usecase

import android.util.Log
import com.fred.app.data.repository.base.ActivityRepository
import com.fred.app.data.repository.base.SuggestionRepository
import com.fred.app.data.repository.model.Suggestion
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetSuggestionsUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val activityRepository: ActivityRepository,
    private val suggestionRepository: SuggestionRepository,
    ) {

  suspend operator fun invoke(): Flow<State<List<Suggestion>>> = flow {
      emit(State.Loading)
      authService.userId?.let {
      activityRepository.getAllActivitiesOf(it).collect {
          when (it) {
            is State.Success -> {
                Log.d("GetSuggestionsUseCase", "Activities: ${it.data}")
              val activities = it.data
              val prompt = "I have done the following activities: " +
                      "${activities.joinToString { it.type.name }}, "
              suggestionRepository.get(prompt).collect {
                when (it) {
                  is State.Success -> {
                    val suggestions = it.data
                    emit(State.Success(suggestions))
                  }
                  is State.Error -> {
                    emit(State.Error(it.exception))
                  }
                  is State.Loading -> {
                    emit(State.Loading)
                  }
                }
              }
            }
            is State.Error -> {
              emit(State.Error(it.exception))
            }
            is State.Loading -> {
              emit(State.Loading)
            }
          }
      }
    } ?: flowOf(State.Error(Exception("")))
  }
}
