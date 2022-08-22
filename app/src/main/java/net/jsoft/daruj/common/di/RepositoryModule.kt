package net.jsoft.daruj.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.jsoft.daruj.common.data.repository.AuthRepositoryImpl
import net.jsoft.daruj.common.data.repository.UserRepositoryImpl
import net.jsoft.daruj.common.data.source.local.DataStoreSettingsDatabase
import net.jsoft.daruj.common.data.source.remote.FirebaseAuthApi
import net.jsoft.daruj.common.data.source.remote.FirebaseUserApi
import net.jsoft.daruj.common.domain.repository.AuthRepository
import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.common.misc.DispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(
        firebaseAuthApi: FirebaseAuthApi,
        dispatcherProvider: DispatcherProvider
    ): AuthRepository = AuthRepositoryImpl(
        authApi = firebaseAuthApi,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    fun provideUserRepository(
        firebaseUserApi: FirebaseUserApi,
        dataStoreSettingsDatabase: DataStoreSettingsDatabase,
        dispatcherProvider: DispatcherProvider
    ): UserRepository = UserRepositoryImpl(
        userApi = firebaseUserApi,
        settingsDatabase = dataStoreSettingsDatabase,
        dispatcherProvider = dispatcherProvider
    )
}