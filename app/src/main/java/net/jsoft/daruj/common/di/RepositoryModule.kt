package net.jsoft.daruj.common.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import net.jsoft.daruj.auth.data.repository.AuthRepositoryImpl
import net.jsoft.daruj.donate_blood.data.repository.ReceiptRepositoryImpl
import net.jsoft.daruj.common.data.repository.UserRepositoryImpl
import net.jsoft.daruj.common.data.source.local.DataStoreSettingsDatabase
import net.jsoft.daruj.auth.data.source.remote.FirebaseAuthApi
import net.jsoft.daruj.donate_blood.data.source.remote.FirebaseReceiptApi
import net.jsoft.daruj.common.data.source.remote.FirebaseUserApi
import net.jsoft.daruj.auth.domain.repository.AuthRepository
import net.jsoft.daruj.donate_blood.domain.repository.ReceiptRepository
import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.common.misc.DispatcherProvider
import net.jsoft.daruj.common.misc.JsonParser
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: FirebaseUserApi,
        settingsDatabase: DataStoreSettingsDatabase,
        application: Application,
        jsonParser: JsonParser,
        dispatcherProvider: DispatcherProvider
    ): UserRepository = UserRepositoryImpl(
        userApi = userApi,
        settingsDatabase = settingsDatabase,
        application = application,
        jsonParser = jsonParser,
        dispatcherProvider = dispatcherProvider
    )
}