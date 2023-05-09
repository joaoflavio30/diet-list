package com.joaoflaviofreitas.dietplan.component.storage.domain

import android.net.Uri

interface FirebaseStorageRepository {

    suspend fun saveImageProfile(uri: Uri?): DataState<Uri?>

    suspend fun deleteImageProfile(): DataState<Boolean>
}
