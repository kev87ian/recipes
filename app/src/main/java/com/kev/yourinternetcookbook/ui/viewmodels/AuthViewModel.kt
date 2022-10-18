package com.kev.yourinternetcookbook.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.kev.yourinternetcookbook.repositories.AuthRepository
import com.kev.yourinternetcookbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
	private val repository: AuthRepository
) : ViewModel() {

	val loginLiveData: MutableLiveData<Resource<FirebaseUser>?> = MutableLiveData()
	val signUpLiveData: MutableLiveData<Resource<FirebaseUser>?> = MutableLiveData()


	fun loginUser(email: String, password: String) = viewModelScope.launch {
		loginLiveData.postValue(Resource.Loading())
		val result = repository.login(email, password)
		loginLiveData.postValue(result)
	}


	fun signupUser(email: String, password: String) = viewModelScope.launch {
		signUpLiveData.postValue(Resource.Loading())
		val result = repository.signUp(email, password)
		signUpLiveData.postValue(result)
	}
}
