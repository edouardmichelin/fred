package com.fred.app.domain.usecase

import com.fred.app.data.repository.base.SuggestionRepository
import com.fred.app.data.repository.model.Suggestion
import com.fred.app.data.repository.model.User
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class GetSuggestionsUseCase
@Inject
constructor(
    private val authService: AuthService,
    private val suggestionRepository: SuggestionRepository,
) {

  suspend operator fun invoke(user: User): Flow<State<List<Suggestion>>> = flow {
    emit(State.Loading)
    authService.userId?.let {
      val promptBuilder = StringBuilder()
      user.activities.forEach {
        promptBuilder.append(it.type)
        promptBuilder.append(" ")
        promptBuilder.append(it.distance)
        promptBuilder.append(" ")
        promptBuilder.append(it.timestamp)
        promptBuilder.append(" ")
        promptBuilder.append(it.vehicleId)
        promptBuilder.append(" ")
        promptBuilder.append(it.impact)
        promptBuilder.append(" ")
      }

      user.transportations.forEach {
        promptBuilder.append(it.type)
        promptBuilder.append(" ")
        promptBuilder.append(it.name)
        promptBuilder.append(" ")
        promptBuilder.append(it.km)
        promptBuilder.append(" ")
        promptBuilder.append(it.age)
      }

      user.locations.forEach {
        promptBuilder.append(it.country)
        promptBuilder.append(" ")
        promptBuilder.append(it.latitude)
        promptBuilder.append(" ")
        promptBuilder.append(it.longitude)
      }

      promptBuilder.append("I follow a ${user.diet.name} diet.")

      suggestionRepository.get(promptBuilder.toString()).collect {
        when (it) {
          is State.Success -> {
            emit(State.Success(it.data))
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
