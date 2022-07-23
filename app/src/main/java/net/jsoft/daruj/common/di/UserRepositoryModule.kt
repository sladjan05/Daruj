package net.jsoft.daruj.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.jsoft.daruj.common.data.FirebaseAuthenticator
import net.jsoft.daruj.common.data.FirebaseUserRepository
import net.jsoft.daruj.common.domain.Authenticator
import net.jsoft.daruj.common.domain.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        firebaseUserRepository: FirebaseUserRepository
    ): UserRepository
}