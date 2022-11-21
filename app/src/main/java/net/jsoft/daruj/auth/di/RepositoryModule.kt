package net.jsoft.daruj.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.auth.data.repository.AuthRepositoryImpl
import net.jsoft.daruj.auth.data.source.remote.FirebaseAuthApi
import net.jsoft.daruj.auth.domain.repository.AuthRepository
import net.jsoft.daruj.common.misc.DispatcherProvider

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        authApi: FirebaseAuthApi,
        dispatcherProvider: DispatcherProvider
    ): AuthRepository = AuthRepositoryImpl(
        authApi = authApi,
        dispatcherProvider = dispatcherProvider
    )
}