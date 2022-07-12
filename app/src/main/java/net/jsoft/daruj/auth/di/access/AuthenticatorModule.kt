package net.jsoft.daruj.auth.di.access

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import net.jsoft.daruj.auth.data.FirebaseAuthenticator
import net.jsoft.daruj.auth.domain.Authenticator

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticatorModule {

    @Binds
    abstract fun bindAuthenticator(
        firebaseAuthenticator: FirebaseAuthenticator
    ): Authenticator
}