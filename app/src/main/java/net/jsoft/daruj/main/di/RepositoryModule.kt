package net.jsoft.daruj.main.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import net.jsoft.daruj.common.misc.DispatcherProvider
import net.jsoft.daruj.main.data.repository.PostRepositoryImpl
import net.jsoft.daruj.main.data.source.remote.FirebasePostApi
import net.jsoft.daruj.main.domain.repository.PostRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePostRepository(
        postApi: FirebasePostApi,
        dispatcherProvider: DispatcherProvider
    ): PostRepository = PostRepositoryImpl(
        postApi = postApi,
        dispatcherProvider = dispatcherProvider
    )
}