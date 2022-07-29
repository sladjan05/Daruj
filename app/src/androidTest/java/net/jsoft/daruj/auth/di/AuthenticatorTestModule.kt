package net.jsoft.daruj.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.jsoft.daruj.auth.data.FakeAuthenticator
import net.jsoft.daruj.auth.data.FirebaseAuthenticator
import net.jsoft.daruj.auth.domain.Authenticator

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticatorTestModule {

    @Binds
    abstract fun bindAuthenticator(
        fakeAuthenticator: FakeAuthenticator
    ): Authenticator
}