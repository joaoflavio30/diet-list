package com.joaoflaviofreitas.dietplan.component.storage.data

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.joaoflaviofreitas.dietplan.component.storage.domain.DataState
import com.joaoflaviofreitas.dietplan.component.storage.domain.FirebaseStorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import javax.inject.Inject

class FirebaseStorageRepositoryImpl @Inject constructor(private val auth: FirebaseAuth, private val storage: FirebaseStorage):
    FirebaseStorageRepository {

    override suspend fun saveImageProfile(uri: String): DataState<String> {
        return withContext(Dispatchers.IO) {
            DataState.Loading
            try {
                val imageProfileUri = Uri.parse(uri)
                val storageRef = FirebaseStorage.getInstance().reference.child("/${auth.currentUser?.uid}/profile.jpg")
                val uploadTask = storageRef.putFile(imageProfileUri).await()

                if (uploadTask.uploadSessionUri != null) {
                    DataState.Success(uploadTask.uploadSessionUri.toString())
                } else {
                    DataState.Error(Exception("Error in upload the profile image"))
                }
            } catch (e: FileNotFoundException) {
                DataState.Error(e)
            }
        }
    }

    override suspend fun deleteImageProfile(): DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            DataState.Loading
            try {
                val storageReference = storage.getReference("/${auth.currentUser?.uid}/profile.jpg")
                storageReference.delete().await()
                if (!storageReference.downloadUrl.isSuccessful) {
                    DataState.Success(true)
                } else {
                    DataState.Error(Exception("Error in delete profile image from the firebase storage"))
                }
            } catch (e: FileNotFoundException) {
                DataState.Error(e)
            }
        }
    }
}
