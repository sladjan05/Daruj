package net.jsoft.daruj.common.data

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.jsoft.daruj.common.data.dto.LocalUserDto
import net.jsoft.daruj.common.domain.UserRepository
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.User
import net.jsoft.daruj.common.util.DispatcherProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserRepository @Inject constructor(
    firestore: FirebaseFirestore,
    storage: FirebaseStorage,
    private val auth: FirebaseAuth,
    private val dispatcherProvider: DispatcherProvider
) : UserRepository {

    private val user = firestore.collection("users").document(auth.currentUser!!.uid)
    private val userStorage = storage.reference.child(auth.currentUser!!.uid)

    private val profilePicture = userStorage.child("profile_picture.png")

    override suspend fun getLocalUser(): LocalUser = withContext(dispatcherProvider.io) {
        val dto = user.get().await().toObject<LocalUserDto>()!!
        val pictureUri = profilePicture.downloadUrl.await()

        dto.getModel(pictureUri)
    }

    override suspend fun updateLocalUser(
        user: LocalUser.Constructable,
        pictureUri: Uri?
    ): Unit = withContext(dispatcherProvider.io) {
        this@FirebaseUserRepository.user.set(user).await()

        if (pictureUri != null) {
            profilePicture.putFile(pictureUri).await()
        }
    }

    override suspend fun getUser(userId: String): User {
        TODO()
    }
}