package com.fred.app.domain.usecase

import com.fred.app.data.repository.model.Activity
import com.fred.app.data.repository.model.ActivityType
import com.fred.app.data.repository.model.Suggestion
import com.fred.app.domain.sdk.AuthService
import com.fred.app.util.State
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPlausibleActionsUseCase
@Inject
constructor(
    private val authService: AuthService,
) {
  suspend operator fun invoke(): Flow<State<List<Suggestion>>> = flow {
    emit(State.Loading)
    val id = authService.userId
    if (id == null) {
      emit(State.Error(Exception("Not logged in")))
      return@flow
    }

    val actions = mutableListOf<Suggestion>()

    if (isAroundWorkTime()) {
      actions.add(
          Suggestion(
              title = "Going back home by car?",
              activity =
                  Activity(
                      type = ActivityType.Commute,
                      impact = 200,
                  )))
      actions.add(
          Suggestion(
              title = "Going back home by bike?",
              activity =
                  Activity(
                      type = ActivityType.Commute,
                      impact = -50,
                  )))
    }

    if (isAroundGoingToWorkTime()) {
      actions.add(
          Suggestion(
              title = "Going to work by car?",
              activity =
                  Activity(
                      type = ActivityType.Commute,
                      impact = 200,
                  )))
      actions.add(
          Suggestion(
              title = "Going to work by bike?",
              activity =
                  Activity(
                      type = ActivityType.Commute,
                      impact = -50,
                  )))
    }

    if (isAroundLunchTime() || isAroundDinerTime()) {
      actions.add(
          Suggestion(
              title = "Ordering food?",
              activity =
                  Activity(
                      type = ActivityType.Eat,
                      impact = 100,
                  )))
      actions.add(
          Suggestion(
              title = "Eating processed food?",
              activity =
                  Activity(
                      type = ActivityType.Eat,
                      impact = 50,
                  )))
      actions.add(
          Suggestion(
              title = "Eating food produced locally?",
              activity =
                  Activity(
                      type = ActivityType.Eat,
                      impact = -40,
                  )))
    }

    emit(State.Success(actions))
  }

  private fun isAroundGoingToWorkTime(): Boolean {
    val start = LocalTime.of(6, 0)
    val end = LocalTime.of(9, 30)

    val currentTime = LocalTime.now()

    return currentTime.isAfter(start) && currentTime.isBefore(end)
  }

  private fun isAroundWorkTime(): Boolean {
    val start = LocalTime.of(9, 30)
    val end = LocalTime.of(19, 0)

    val currentTime = LocalTime.now()

    return currentTime.isAfter(start) && currentTime.isBefore(end)
  }

  private fun isAroundLunchTime(): Boolean {
    val start = LocalTime.of(11, 30)
    val end = LocalTime.of(15, 0)

    val currentTime = LocalTime.now()

    return currentTime.isAfter(start) && currentTime.isBefore(end)
  }

    private fun isAroundDinerTime(): Boolean {
        val start = LocalTime.of(18, 30)
        val end = LocalTime.of(21, 30)

        val currentTime = LocalTime.now()

        return currentTime.isAfter(start) && currentTime.isBefore(end)
    }

}
