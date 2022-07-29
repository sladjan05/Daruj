package net.jsoft.daruj.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import net.jsoft.daruj.common.util.DispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherTestModule {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProvider(
            io = StandardTestDispatcher(),
            default = StandardTestDispatcher(),
            main = StandardTestDispatcher(),
            unconfined = StandardTestDispatcher()
        )
    }
}