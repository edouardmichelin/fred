package com.fred.app.di

import com.fred.app.data.datasource.base.ChatDataSource
import com.fred.app.data.datasource.base.GetUserDataSource
import com.fred.app.data.datasource.base.LoginDataSource
import com.fred.app.data.datasource.base.RegisterDataSource
import com.fred.app.data.datasource.impl.ChatDataSourceImpl
import com.fred.app.data.datasource.impl.GetUserDataSourceImpl
import com.fred.app.data.datasource.impl.LoginDataSourceImpl
import com.fred.app.data.datasource.impl.RegisterDataSourceImpl
import com.fred.app.data.repository.base.ChatRepository
import com.fred.app.data.repository.base.GetUserRepository
import com.fred.app.data.repository.base.LoginRepository
import com.fred.app.data.repository.base.RegisterRepository
import com.fred.app.data.repository.impl.ChatRepositoryImpl
import com.fred.app.data.repository.impl.GetUserRepositoryImpl
import com.fred.app.data.repository.impl.LoginRepositoryImpl
import com.fred.app.data.repository.impl.RegisterRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

  @Provides fun provideLoginDataSource(): LoginDataSource = LoginDataSourceImpl()

  @Provides
  fun provideLoginRepository(
      loginDataSource: LoginDataSource,
  ): LoginRepository = LoginRepositoryImpl(loginDataSource)

  @Provides
  fun provideRegisterDataSource(
      firebaseFirestore: FirebaseFirestore,
  ): RegisterDataSource = RegisterDataSourceImpl(firebaseFirestore)

  @Provides
  fun provideRegisterRepository(
      registerDataSource: RegisterDataSource,
  ): RegisterRepository = RegisterRepositoryImpl(registerDataSource)

  @Provides
  fun provideGetUserDataSource(
      firebaseFirestore: FirebaseFirestore,
  ): GetUserDataSource = GetUserDataSourceImpl(firebaseFirestore)

  @Provides
  fun provideGetUserRepository(
      getUserDataSource: GetUserDataSource,
  ): GetUserRepository = GetUserRepositoryImpl(getUserDataSource)

  @Provides
  fun provideChatDataSource(
      firebaseFirestore: FirebaseFirestore,
  ): ChatDataSource = ChatDataSourceImpl(firebaseFirestore)

  @Provides
  fun provideChatRepository(
      chatDataSource: ChatDataSource,
  ): ChatRepository = ChatRepositoryImpl(chatDataSource)
}
