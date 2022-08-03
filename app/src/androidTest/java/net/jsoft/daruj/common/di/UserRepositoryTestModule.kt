package net.jsoft.daruj.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.jsoft.daruj.common.data.FakeUserRepository
import net.jsoft.daruj.common.data.FirebaseUserRepository
import net.jsoft.daruj.common.domain.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryTestModule {

    @Binds
    abstract fun bindUserRepository(
        fakeUserRepository: FakeUserRepository
    ): UserRepository
}