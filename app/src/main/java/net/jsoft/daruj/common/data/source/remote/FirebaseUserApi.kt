package net.jsoft.daruj.common.data.source.remote

import android.app.Application
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import net.jsoft.daruj.common.data.source.remote.dto.LocalUserDto
import net.jsoft.daruj.common.data.source.remote.dto.UserDto
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.User
import net.jsoft.daruj.common.misc.JsonParser
import net.jsoft.daruj.common.misc.fromJson
import net.jsoft.daruj.common.util.awaitOrNull
import net.jsoft.daruj.common.util.compressToByteArray
import net.jsoft.daruj.common.util.getBitmap
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserApi @Inject constructor(
    private val application: Application,

    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val functions: FirebaseFunctions,

    private val jsonParser: JsonParser
) : UserApi {

    private val usersStorage: StorageReference
        get() = storage.reference.child(Users)

    private val localUserReference: DocumentReference
        get() = firestore.collection(Users).document(auth.currentUser!!.uid)

    private val localUserPicture: StorageReference
        get() = usersStorage.child("${auth.currentUser!!.uid}.png")

    override suspend fun hasCompletedRegistration(): Boolean {
        val localUserSnapshot = localUserReference.get().await()
        return localUserSnapshot.exists()
    }

    override suspend fun getUser(id: String): User {
        val deferredUserJson = functions
            .getHttpsCallable("getUser")
            .call(id)
            .asDeferred()

        val deferredPictureUri = usersStorage
            .child("$id.png")
            .downloadUrl
            .asDeferred()

        val userDto = jsonParser.fromJson<UserDto>(deferredUserJson.await().data as String)
        return userDto.getModel(deferredPictureUri.awaitOrNull())
    }

    override suspend fun getLocalUser(): LocalUser {
        val deferredPictureUri = localUserPicture.downloadUrl.asDeferred()
        val localUserDto = localUserReference.get().await().toObject<LocalUserDto>()!!

        return localUserDto.getModel(deferredPictureUri.awaitOrNull())
    }

    override suspend fun updateLocalUser(user: LocalUser.Mutable) {
        localUserReference
            .set(user, SetOptions.merge())
            .await()
    }

    override suspend fun updateProfilePicture(pictureUri: Uri) {
        val bitmap = application.getBitmap(
            uri = pictureUri,
            width = 300,
            height = 300
        )

        val compressed = bitmap?.compressToByteArray()

        val metadata = StorageMetadata.Builder()
            .setContentType("image/png")
            .build()

        localUserPicture.putBytes(compressed!!, metadata).await()
    }

    override suspend fun setPostSaved(postId: String, saved: Boolean) {
        val fieldValue = if (saved) FieldValue.arrayUnion(postId) else FieldValue.arrayRemove(postId)

        localUserReference
            .update(LocalUser.Immutable::savedPosts.name, fieldValue)
            .await()
    }

    companion object {
        private const val Users = "users"
    }
}