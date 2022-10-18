package com.kev.yourinternetcookbook.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kev.yourinternetcookbook.data.remote.models.MealsUnderCategoryResponse
import com.kev.yourinternetcookbook.repositories.SearchMealsRepository
import com.kev.yourinternetcookbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchMealsViewModel @Inject constructor(private val searchMealsRepository: SearchMealsRepository	) : ViewModel() {


	val searchMealObservableResponse: MutableLiveData<Resource<MealsUnderCategoryResponse>> =
		MutableLiveData()


	suspend fun searchMeals(mealName:String) {
		searchMealsRepository.searchMeals(mealName).collect{
			searchMealObservableResponse.postValue(it)
		}
	}

}