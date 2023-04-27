package com.example.dietplan.data

import android.util.Log
import com.example.dietplan.domain.model.UserAuth
import com.example.dietplan.domain.repository.FirebaseAuthRepository
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) :
    FirebaseAuthRepository {
    override suspend fun signUp(userAuth: UserAuth): com.example.dietplan.domain.model.DataState<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.createUserWithEmailAndPassword(userAuth.email, userAuth.password).await()
                Log.d("TAG", "sucess ${result.user}")
                if (result.user != null) {
                    com.example.dietplan.domain.model.DataState.Success(result.user!!)
                } else { Log.d("TAG", "sucess ${result.user}")
                    com.example.dietplan.domain.model.DataState.Error(Exception("FirebaseAuth Error"))
                }
            } catch (e:FirebaseAuthWeakPasswordException) {
                Log.d("TAG", "sucess $e")
                com.example.dietplan.domain.model.DataState.Error(e)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.d("TAG", "sucess $e -- ${userAuth.email}")
                com.example.dietplan.domain.model.DataState.Error(e)
            } catch (e: FirebaseAuthUserCollisionException) {
                Log.d("TAG", "sucess $e")
                com.example.dietplan.domain.model.DataState.Error(e)
            } catch (e: Exception) {
                Log.d("TAG", "sucess $e")
                com.example.dietplan.domain.model.DataState.Error(e)
            }
        }
    }

    override suspend fun signIn(userAuth: UserAuth): com.example.dietplan.domain.model.DataState<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.signInWithEmailAndPassword(userAuth.email, userAuth.password).await()
                if (result.user != null) {
                    com.example.dietplan.domain.model.DataState.Success(result.user!!)
                } else {
                    com.example.dietplan.domain.model.DataState.Error(Exception("Failed to sign in"))
                }
            } catch (e: FirebaseAuthInvalidUserException) {
                com.example.dietplan.domain.model.DataState.Error(e)
            } catch (e:FirebaseAuthInvalidCredentialsException) {
                com.example.dietplan.domain.model.DataState.Error(e)
            }
        }
    }

    override suspend fun signInWithGoogle(credential: AuthCredential): com.example.dietplan.domain.model.DataState<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            try {
                com.example.dietplan.domain.model.DataState.Loading
                val result = auth.signInWithCredential(credential).await()
                if (result.user != null) {
                    com.example.dietplan.domain.model.DataState.Success(result.user!!)
                } else {
                    com.example.dietplan.domain.model.DataState.Error(Exception("Failed to sign in with Google"))
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                com.example.dietplan.domain.model.DataState.Error(e)
            } catch (e: ApiException) {
                com.example.dietplan.domain.model.DataState.Error(e)
            } catch (e: FirebaseAuthUserCollisionException) {
                com.example.dietplan.domain.model.DataState.Error(e)
            } finally {
                cancel()
            }
        }
    }

    override suspend fun userIsLogged(): com.example.dietplan.domain.model.DataState<Boolean> {
        return withContext(Dispatchers.Main) {
            try {
                com.example.dietplan.domain.model.DataState.Loading
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    com.example.dietplan.domain.model.DataState.Success(true)
                } else {
                    com.example.dietplan.domain.model.DataState.Error(Exception("Current user = null"))
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                com.example.dietplan.domain.model.DataState.Error(e)
            } catch (e: FirebaseAuthInvalidUserException) {
                com.example.dietplan.domain.model.DataState.Error(e)
            } catch (e: FirebaseAuthUserCollisionException) {
                com.example.dietplan.domain.model.DataState.Error(e)
            } catch (e: FirebaseAuthException) {
                com.example.dietplan.domain.model.DataState.Error(e)
            } catch (e: Exception) {
                com.example.dietplan.domain.model.DataState.Error(e)
            }
        }
    }

    override suspend fun restorePasswordByEmail(email: String): com.example.dietplan.domain.model.DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            com.example.dietplan.domain.model.DataState.Loading
            try {
                auth.sendPasswordResetEmail(email).await()
                com.example.dietplan.domain.model.DataState.Success(true)
            } catch (e: Exception) {
                com.example.dietplan.domain.model.DataState.Error(e)
            }
        }
    }

    override suspend fun changePassword(
        currentUser: FirebaseUser,
        password: String,
    ): com.example.dietplan.domain.model.DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            com.example.dietplan.domain.model.DataState.Loading
            try {
                currentUser.updatePassword(password)
                com.example.dietplan.domain.model.DataState.Success(true)
            } catch (e: FirebaseAuthException) {
                com.example.dietplan.domain.model.DataState.Error(e)
            }
        }
    }

    override suspend fun changeEmail(currentUser: FirebaseUser, email: String): com.example.dietplan.domain.model.DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            com.example.dietplan.domain.model.DataState.Loading
            try {
                currentUser.updatePassword(email)
                com.example.dietplan.domain.model.DataState.Success(true)
            } catch (e: FirebaseAuthException) {
                com.example.dietplan.domain.model.DataState.Error(e)
            }
        }
    }
}
