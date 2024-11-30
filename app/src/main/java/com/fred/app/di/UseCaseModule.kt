package com.fred.app.di

import com.fred.app.data.repository.base.GetUserRepository
import com.fred.app.data.repository.base.LoginRepository
import com.fred.app.data.repository.base.RegisterRepository
import com.fred.app.domain.sdk.AuthService
import com.fred.app.domain.usecase.GetUserUseCase
import com.fred.app.domain.usecase.LoginUseCase
import com.fred.app.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

  @ViewModelScoped
  @Provides
  fun provideLoginUseCase(
      loginRepository: LoginRepository,
  ) = LoginUseCase(loginRepository)

  @ViewModelScoped
  @Provides
  fun provideRegisterUseCase(
      authService: AuthService,
      registerRepository: RegisterRepository,
  ) = RegisterUseCase(authService, registerRepository)

  @ViewModelScoped
  @Provides
  fun provideGetUserUseCase(
      authService: AuthService,
      getUserRepository: GetUserRepository,
  ) = GetUserUseCase(authService, getUserRepository)

}
