package com.fred.app.util

sealed class State<out T> {
  class Success<out T>(val data: T) : State<T>()

  class Error(val exception: Exception) : State<Nothing>()
}
