package com.example.dietplan.repositories

import com.example.dietplan.DataState
import com.firebase.ui.auth.data.model.User
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    suspend fun register(): Flow<DataState<User>>
}
