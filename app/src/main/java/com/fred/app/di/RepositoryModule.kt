package com.fred.app.di

import com.fred.app.data.datasource.base.LoginDataSource
import com.fred.app.data.datasource.impl.LoginDataSourceImpl
import com.fred.app.data.repository.base.ActivityRepository
import com.fred.app.data.repository.base.LocationRepository
import com.fred.app.data.repository.base.LoginRepository
import com.fred.app.data.repository.base.RegisterUserRepository
import com.fred.app.data.repository.base.SuggestionRepository
import com.fred.app.data.repository.base.UserRepository
import com.fred.app.data.repository.base.VehicleRepository
import com.fred.app.data.repository.impl.ActivityRepositoryImpl
import com.fred.app.data.repository.impl.LocationRepositoryImpl
import com.fred.app.data.repository.impl.LoginRepositoryImpl
import com.fred.app.data.repository.impl.RegisterUserRepositoryImpl
import com.fred.app.data.repository.impl.SuggestionRepositoryImpl
import com.fred.app.data.repository.impl.UserRepositoryImpl
import com.fred.app.data.repository.impl.VehicleRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

  @Provides fun provideLoginDataSource(): LoginDataSource = LoginDataSourceImpl()

  @Provides
  fun provideLoginRepository(
      loginDataSource: LoginDataSource,
  ): LoginRepository = LoginRepositoryImpl(loginDataSource)

  @Provides
  fun provideGetUserRepository(
      db: FirebaseFirestore,
  ): UserRepository = UserRepositoryImpl(db)

  @Provides
  fun provideVehicleRepository(
      db: FirebaseFirestore,
  ): VehicleRepository = VehicleRepositoryImpl(db)

  @Provides
  fun provideLocationRepository(
      db: FirebaseFirestore,
      client: OkHttpClient,
  ): LocationRepository = LocationRepositoryImpl(db, client)

  @Provides
  fun provideActivityRepository(
      db: FirebaseFirestore,
  ): ActivityRepository = ActivityRepositoryImpl(db)

  @Provides
  fun provideRegisterUserRepository(
      db: FirebaseFirestore,
  ): RegisterUserRepository = RegisterUserRepositoryImpl(db)

  @Provides
  fun provideSuggestionRepository(
      client: OkHttpClient,
  ): SuggestionRepository = SuggestionRepositoryImpl(client)
}
