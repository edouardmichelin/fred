package com.fred.app.di

import com.fred.app.domain.sdk.AuthService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

  @Provides
  @Singleton
  fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

  @Provides
  @Singleton
  fun provideFirebaseAuth(): FirebaseAuth {
    return FirebaseAuth.getInstance()
  }

  @Provides
  @Singleton
  fun provideAuthService(firebaseAuth: FirebaseAuth): AuthService {
    return AuthService(firebaseAuth)
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient = OkHttpClient()
}
