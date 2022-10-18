package com.kev.yourinternetcookbook.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.yourinternetcookbook.data.remote.models.MealsUnderCategoryResponse
import com.kev.yourinternetcookbook.repositories.MealsUnderCategoryRepository
import com.kev.yourinternetcookbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsUnderCategoryViewModel @Inject constructor(
	private val repository: MealsUnderCategoryRepository,
	savedStateHandle: SavedStateHandle
) : ViewModel() {

	val observableMealsUnderCategoriesResponse: MutableLiveData<Resource<MealsUnderCategoryResponse>> = MutableLiveData()

	private val mealName = savedStateHandle.get<String>("categoryName")


	fun fetchMealsUnderCategories(categoryName:String) = viewModelScope.launch {
			repository.fetchMealsUnderCategory(categoryName).collect{
				observableMealsUnderCategoriesResponse.postValue(it)
			}
	}

	init {
		fetchMealsUnderCategories(mealName!!)
		Log.i("Clicked Category", mealName)
	}
}