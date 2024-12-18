package com.fred.app.util

class CommonException(message: String = "Something went wrong") : Exception(message)

class UserNotFoundException(message: String) : Exception(message)

class ChatNotFoundException(message: String) : Exception(message)
