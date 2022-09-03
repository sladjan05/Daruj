package net.jsoft.daruj.common.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import net.jsoft.daruj.common.data.repository.AuthRepositoryImpl
import net.jsoft.daruj.common.data.repository.UserRepositoryImpl
import net.jsoft.daruj.common.data.source.local.DataStoreSettingsDatabase
import net.jsoft.daruj.common.data.source.remote.FirebaseAuthApi
import net.jsoft.daruj.common.data.source.remote.FirebaseUserApi
import net.jsoft.daruj.common.domain.repository.AuthRepository
import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.common.misc.DispatcherProvider
import net.jsoft.daruj.common.misc.JsonParser

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        firebaseAuthApi: FirebaseAuthApi,
        dispatcherProvider: DispatcherProvider
    ): AuthRepository = AuthRepositoryImpl(
        authApi = firebaseAuthApi,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        firebaseUserApi: FirebaseUserApi,
        dataStoreSettingsDatabase: DataStoreSettingsDatabase,
        application: Application,
        jsonParser: JsonParser,
        dispatcherProvider: DispatcherProvider
    ): UserRepository = UserRepositoryImpl(
        userApi = firebaseUserApi,
        settingsDatabase = dataStoreSettingsDatabase,
        application = application,
        jsonParser = jsonParser,
        dispatcherProvider = dispatcherProvider
    )
}