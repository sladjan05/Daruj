package net.jsoft.daruj.common.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
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

    private const val USE_EMULATORS = true
    private const val HOST = "192.168.102.192"

    private const val AUTH_PORT = 9099
    private const val FIRESTORE_PORT = 8081
    private const val STORAGE_PORT = 9199
    private const val FUNCTIONS_PORT = 5001

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth.apply {
            if (USE_EMULATORS) useEmulator(HOST, AUTH_PORT)
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore.apply {
            if (USE_EMULATORS) useEmulator(HOST, FIRESTORE_PORT)
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return Firebase.storage.apply {
            if (USE_EMULATORS) useEmulator(HOST, STORAGE_PORT)
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseFunctions(): FirebaseFunctions {
        return Firebase.functions.apply {
            if (USE_EMULATORS) useEmulator(HOST, FUNCTIONS_PORT)
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseMessaging(): FirebaseMessaging {
        return Firebase.messaging
    }
}