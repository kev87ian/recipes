package com.kev.yourinternetcookbook.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.kev.yourinternetcookbook.utils.Resource
import com.kev.yourinternetcookbook.utils.await
import java.io.IOException
import javax.inject.Inject


class AuthRepository @Inject constructor(
	private val firebaseAuth: FirebaseAuth
) {
	suspend fun login(email: String, password: String): Resource<FirebaseUser> {
		return try {
			val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
			Resource.Success(result.user!!)
		} catch (e: Exception) {
				when(e){
					is FirebaseAuthInvalidCredentialsException -> Resource.Error("Invalid credentials. Please try again.")
					is IOException -> Resource.Error("Ensure you have an active internet connection")
					is FirebaseAuthInvalidUserException -> Resource.Error("This account does not exists.")

					else -> Resource.Error(e.localizedMessage.toString())
				}
		}
	}

	suspend fun signUp(email: String, password: String): Resource<FirebaseUser> {
		return try {
			val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

			return Resource.Success(result.user!!)
		} catch (e: Exception){

			when(e)
			{
				is IOException -> Resource.Error("Ensure you have an active internet connection.")
				is FirebaseAuthUserCollisionException -> Resource.Error("An account with this email already exists.")
				else -> Resource.Error(e.localizedMessage)
			}
		}

	}

	fun logout(){
		firebaseAuth.signOut()
	}
}