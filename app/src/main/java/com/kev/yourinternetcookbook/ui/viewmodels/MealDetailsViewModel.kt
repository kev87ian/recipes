package com.kev.yourinternetcookbook.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.yourinternetcookbook.data.remote.models.Meal
import com.kev.yourinternetcookbook.data.remote.models.MealsUnderCategoryResponse
import com.kev.yourinternetcookbook.repositories.MealDetailsRepository
import com.kev.yourinternetcookbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
	private val repository: MealDetailsRepository,
	savedStateHandle: SavedStateHandle
) : ViewModel() {

	val observableMealDetailsResponse : MutableLiveData<Resource<MealsUnderCategoryResponse>> = MutableLiveData()

	private val mealId = savedStateHandle.get<String>("mealId")

	fun fetchMealDetails(mealID : String) = viewModelScope.launch {
		repository.fetchMealDetails(mealID).collect{
			observableMealDetailsResponse.postValue(it)
		}
	}

	fun saveRecipe(meal:Meal ) = viewModelScope.launch {
		repository.saveRecipe(meal)
	}

	init {
		fetchMealDetails(mealId!!)
		Log.i("MealID", mealId)
	}
}