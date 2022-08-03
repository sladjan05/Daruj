package net.jsoft.daruj.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.jsoft.daruj.common.util.DispatcherProvider
import net.jsoft.daruj.common.util.TestDispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherTestModule {

    @Provides
    @Singleton
    @OptIn(ExperimentalCoroutinesApi::class)
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProvider(
            io = TestDispatchers.IO,
            default = TestDispatchers.Default,
            main = TestDispatchers.Main,
            unconfined = TestDispatchers.Unconfined
        )
    }
}
