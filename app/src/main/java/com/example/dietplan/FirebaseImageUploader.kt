package com.example.dietplan

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class FirebaseImageUploader {

    fun uploadImageToFirebaseStorage(uri: Uri, successCallback: (String) -> Unit, errorCallback: (Exception) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("profile_images/${FirebaseAuth.getInstance().currentUser!!.uid}")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                successCallback(downloadUri)
            } else {
                errorCallback(task.exception!!)
            }
        }
    }

    fun loadImageFromFirebaseStorage(imageUrl: String, successCallback: (Uri) -> Unit, errorCallback: (Exception) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child(imageUrl)
        imageRef.downloadUrl
            .addOnSuccessListener { uri ->
                successCallback(uri)
            }
            .addOnFailureListener { exception ->
                errorCallback(exception)
            }
    }
}
