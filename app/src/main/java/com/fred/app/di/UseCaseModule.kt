package com.fred.app.di

import com.fred.app.data.repository.base.ActivityRepository
import com.fred.app.data.repository.base.UserRepository
import com.fred.app.data.repository.base.LocationRepository
import com.fred.app.data.repository.base.LoginRepository
import com.fred.app.data.repository.base.RegisterUserRepository
import com.fred.app.data.repository.base.SuggestionRepository
import com.fred.app.data.repository.base.VehicleRepository
import com.fred.app.domain.sdk.AuthService
import com.fred.app.domain.usecase.CreateActivityUseCase
import com.fred.app.domain.usecase.CreateLocationUseCase
import com.fred.app.domain.usecase.CreateVehicleUseCase
import com.fred.app.domain.usecase.GetActivitiesUseCase
import com.fred.app.domain.usecase.GetLocationsUseCase
import com.fred.app.domain.usecase.GetPlausibleActionsUseCase
import com.fred.app.domain.usecase.GetSuggestionsUseCase
import com.fred.app.domain.usecase.GetUserUseCase
import com.fred.app.domain.usecase.GetVehiclesUseCase
import com.fred.app.domain.usecase.LoginUseCase
import com.fred.app.domain.usecase.RegisterUserUseCase
import com.fred.app.domain.usecase.SearchLocationUseCase
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
    fun provideGetUserUseCase(
        authService: AuthService,
        userRepository: UserRepository,
        vehicleRepository: VehicleRepository,
        activityRepository: ActivityRepository,
        locationRepository: LocationRepository
    ) = GetUserUseCase(authService, userRepository, vehicleRepository, activityRepository, locationRepository)

    @ViewModelScoped
    @Provides
    fun provideGetActivitiesUseCase(
        authService: AuthService,
        activityRepository: ActivityRepository,
    ) = GetActivitiesUseCase(authService, activityRepository)

    @ViewModelScoped
    @Provides
    fun provideGetLocationsUseCase(
        authService: AuthService,
        locationRepository: LocationRepository,
    ) = GetLocationsUseCase(authService, locationRepository)

    @ViewModelScoped
    @Provides
    fun provideSearchLocationUseCase(
        locationRepository: LocationRepository,
    ) = SearchLocationUseCase(locationRepository)

    @ViewModelScoped
    @Provides
    fun provideGetVehiclesUseCase(
        authService: AuthService,
        vehicleRepository: VehicleRepository,
    ) = GetVehiclesUseCase(authService, vehicleRepository)

    @ViewModelScoped
    @Provides
    fun provideCreateActivityUseCase(
        authService: AuthService,
        userRepository: UserRepository,
        vehicleRepository: VehicleRepository,
        activityRepository: ActivityRepository,
    ) = CreateActivityUseCase(authService, userRepository, vehicleRepository, activityRepository)

    @ViewModelScoped
    @Provides
    fun provideCreateLocationUseCase(
        authService: AuthService,
        locationRepository: LocationRepository,
    ) = CreateLocationUseCase(authService, locationRepository)

    @ViewModelScoped
    @Provides
    fun provideCreateVehicleUseCase(
        authService: AuthService,
        vehicleRepository: VehicleRepository,
    ) = CreateVehicleUseCase(authService, vehicleRepository)

    @ViewModelScoped
    @Provides
    fun provideRegisterUserUseCase(
        authService: AuthService,
        registerUserRepository: RegisterUserRepository,
    ) = RegisterUserUseCase(authService, registerUserRepository)

    @ViewModelScoped
    @Provides
    fun provideGetSuggestionsUseCase(
        authService: AuthService,
        suggestionRepository: SuggestionRepository,
    ) = GetSuggestionsUseCase(authService, suggestionRepository)

    @ViewModelScoped
    @Provides
    fun provideGetPlausibleActionsUseCase(
        authService: AuthService,
    ) = GetPlausibleActionsUseCase(authService)
}
