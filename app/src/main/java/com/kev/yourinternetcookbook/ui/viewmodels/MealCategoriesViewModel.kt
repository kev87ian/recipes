package com.kev.yourinternetcookbook.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.yourinternetcookbook.data.remote.models.MealCategoriesItem
import com.kev.yourinternetcookbook.repositories.MealCategoriesRepository
import com.kev.yourinternetcookbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealCategoriesViewModel @Inject constructor(
	private val repository: MealCategoriesRepository
) :
	ViewModel() {
	val observableMealCategoriesResponse: MutableLiveData<Resource<List<MealCategoriesItem>>> = MutableLiveData()

	/*
	* The viewmodel collects whatever the flow emits in the repository, and posts it values to the observable
	 */
	fun getAllCategories() = viewModelScope.launch {
		repository.fetchMealCategories().collect{


			observableMealCategoriesResponse.postValue(it)

		}
	}



	init {

		getAllCategories()

	}

}