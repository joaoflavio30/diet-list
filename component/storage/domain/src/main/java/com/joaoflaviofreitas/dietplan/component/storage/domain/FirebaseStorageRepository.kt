package com.joaoflaviofreitas.dietplan.component.storage.domain

interface FirebaseStorageRepository {

    suspend fun saveImageProfile(uri: String): DataState<String>

    suspend fun deleteImageProfile(): DataState<Boolean>
}
