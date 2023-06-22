package com.joaoflaviofreitas.dietplan.component.storage.data

import android.net.Uri
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.joaoflaviofreitas.dietplan.component.storage.domain.DataState
import com.joaoflaviofreitas.dietplan.component.storage.domain.FirebaseStorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import javax.inject.Inject

internal class FirebaseStorageRepositoryImpl @Inject constructor(private val auth: FirebaseAuth, private val storage: FirebaseStorage):
    FirebaseStorageRepository {

    override suspend fun saveImageProfile(uri: Uri?): DataState<Uri?> {
        return withContext(Dispatchers.IO) {
            try {
                DataState.Loading
                if (uri != null) {
                    val storageRef = storage.reference.child("profile_images/${auth.currentUser?.uid}/profile.jpg")
                    val uploadTask = storageRef.putFile(uri).await()
                    if (uploadTask.metadata != null) {
                        Log.d("erro", "nao teve erro: ${uploadTask.uploadSessionUri}")
                        DataState.Success(uploadTask.uploadSessionUri)
                    } else {
                        Log.d("erro", "caiu no else")
                        DataState.Error(Exception("Error in upload the profile image"))
                    }
                }
                DataState.Error(Exception("uri = null"))
            } catch (e: FileNotFoundException) {
                Log.d("erro", "caiu no catch: $e")
                DataState.Error(e)
            }
        }
    }

    override suspend fun deleteImageProfile(): DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            try { DataState.Loading
                val storageRef = storage.reference.child("profile_images/${auth.currentUser?.uid}/profile.jpg")
                if (storageRef.parent != null) storageRef.delete().await()
                if (!storageRef.downloadUrl.isSuccessful) {
                    DataState.Success(true)
                } else {
                    DataState.Error(Exception("Error in delete profile image from the firebase storage"))
                }
            } catch (e: StorageException) {
                DataState.Error(e)
            }
        }
    }
    override suspend fun getMetadataOfImage(): Long {
        return withContext(Dispatchers.IO) {
            try {
                val storageRef = storage.reference.child("profile_images/${auth.currentUser?.uid}/profile.jpg")
                val metaData = storageRef.metadata.await()
                metaData.updatedTimeMillis
            } catch (e: FirebaseException) {
                throw e
            }
        }
    }
}
