package com.joaoflaviofreitas.dietplan.component.authentication.data

import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.* // ktlint-disable no-wildcard-imports
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.UserAuth
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) :
    FirebaseAuthRepository {
    override suspend fun signUp(userAuth: UserAuth): DataState<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.createUserWithEmailAndPassword(userAuth.email, userAuth.password).await()
                Log.d("TAG", "sucess ${result.user}")
                if (result.user != null) {
                    DataState.Success(result.user!!)
                } else { Log.d("TAG", "sucess ${result.user}")
                    DataState.Error(Exception("FirebaseAuth Error"))
                }
            } catch (e:FirebaseAuthWeakPasswordException) {
                Log.d("TAG", "sucess $e")
                DataState.Error(e)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.d("TAG", "sucess $e -- ${userAuth.email}")
                DataState.Error(e)
            } catch (e: FirebaseAuthUserCollisionException) {
                Log.d("TAG", "sucess $e")
                DataState.Error(e)
            } catch (e: Exception) {
                Log.d("TAG", "sucess $e")
                DataState.Error(e)
            }
        }
    }

    override suspend fun signIn(userAuth: UserAuth): DataState<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.signInWithEmailAndPassword(userAuth.email, userAuth.password).await()
                if (result.user != null) {
                    DataState.Success(result.user!!)
                } else {
                    DataState.Error(Exception("Failed to sign in"))
                }
            } catch (e: FirebaseAuthInvalidUserException) {
                DataState.Error(e)
            } catch (e:FirebaseAuthInvalidCredentialsException) {
                DataState.Error(e)
            }
        }
    }

    override suspend fun signInWithGoogle(credential: AuthCredential): DataState<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            try {
                DataState.Loading
                val result = auth.signInWithCredential(credential).await()
                if (result.user != null) {
                    DataState.Success(result.user!!)
                } else {
                    DataState.Error(Exception("Failed to sign in with Google"))
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                DataState.Error(e)
            } catch (e: ApiException) {
                DataState.Error(e)
            } catch (e: FirebaseAuthUserCollisionException) {
                DataState.Error(e)
            }
        }
    }

    override suspend fun userIsLogged(): DataState<Boolean> {
        return withContext(Dispatchers.Main) {
            try {
                DataState.Loading
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    DataState.Success(true)
                } else {
                    DataState.Error(Exception("Current user = null"))
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                DataState.Error(e)
            } catch (e: FirebaseAuthInvalidUserException) {
                DataState.Error(e)
            } catch (e: FirebaseAuthUserCollisionException) {
                DataState.Error(e)
            } catch (e: FirebaseAuthException) {
                DataState.Error(e)
            } catch (e: Exception) {
                DataState.Error(e)
            }
        }
    }

    override suspend fun restorePasswordByEmail(email: String): DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            DataState.Loading
            try {
                auth.sendPasswordResetEmail(email).await()
                DataState.Success(true)
            } catch (e: Exception) {
                DataState.Error(e)
            }
        }
    }

    override suspend fun changePassword(
        currentUser: FirebaseUser,
        password: String,
    ): DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            DataState.Loading
            try {
                currentUser.updatePassword(password)
                DataState.Success(true)
            } catch (e: FirebaseAuthException) {
                DataState.Error(e)
            }
        }
    }

    override suspend fun changeEmail(currentUser: FirebaseUser, email: String): DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            DataState.Loading
            try {
                currentUser.updatePassword(email)
                DataState.Success(true)
            } catch (e: FirebaseAuthException) {
                DataState.Error(e)
            }
        }
    }
}
