package net.jsoft.daruj.common.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    private const val useEmulators = false
    private const val host = "127.0.0.1"

    private const val authPort = 9099
    private const val firestorePort = 8080

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth.apply {
            if (useEmulators) useEmulator(host, authPort)
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore.apply {
            if (useEmulators) useEmulator(host, firestorePort)
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage() : FirebaseStorage  {
        return Firebase.storage.apply {
            if(useEmulators) TODO()
        }
    }
}