package com.fred.app.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : IViewState, Event : IViewEvent> : ViewModel() {

  private val initialState: State by lazy { createInitialState() }

  abstract fun createInitialState(): State

  val state: State
    get() = uiState.value

  abstract fun triggerEvent(event: Event)

  private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
  val uiState: StateFlow<State> = _uiState

  private val _uiEvent: MutableSharedFlow<Event> = MutableSharedFlow()
  val uiEvent = _uiEvent.asSharedFlow()

  protected fun setState(reduce: State.() -> State) {
    val newState = state.reduce()
    _uiState.value = newState
    Log.d("TAG", "$newState")
  }

  protected fun setEvent(event: Event) {
    viewModelScope.launch { _uiEvent.emit(event) }
  }

  protected suspend fun <T> call(callFlow: Flow<T>, completionHandler: (collect: T) -> Unit = {}) {
    callFlow.catch {}.collect { completionHandler.invoke(it) }
  }
}
