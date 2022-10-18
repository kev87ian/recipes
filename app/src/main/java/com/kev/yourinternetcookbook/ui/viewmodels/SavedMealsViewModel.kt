package com.kev.yourinternetcookbook.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.kev.yourinternetcookbook.data.remote.models.Meal
import com.kev.yourinternetcookbook.repositories.SavedMealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedMealsViewModel @Inject constructor(
	private val repository: SavedMealsRepository
) : ViewModel() {

	fun fetchMealsFromDatabase() = repository.fetchMealsFromDatabase()


	fun deleteMealsDatabase(meal: Meal) = repository.deleteMeal(meal)

	fun checkIfDbIsEmpty() = repository.checkIfDbIsEmpty()
}