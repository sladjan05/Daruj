package net.jsoft.daruj.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.jsoft.daruj.common.data.DataStoreLocalSettingsRepository
import net.jsoft.daruj.common.data.FakeLocalSettingsRepository
import net.jsoft.daruj.common.domain.LocalSettingsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalSettingsRepositoryTestModule {

    @Binds
    abstract fun bindLocalSettingsRepository(
        fakeLocalSettingsRepository: FakeLocalSettingsRepository
    ): LocalSettingsRepository
}