package com.kev.yourinternetcookbook.repositories

import com.kev.yourinternetcookbook.data.local.db.FavoritesDao
import com.kev.yourinternetcookbook.data.remote.models.Meal
import javax.inject.Inject

class SavedMealsRepository @Inject constructor (private val favoritesDao: FavoritesDao ) {

	fun fetchMealsFromDatabase() = favoritesDao.getSavedRecipes()

	 fun deleteMeal(meal: Meal) = favoritesDao.deleteRecipes(meal)
	fun checkIfDbIsEmpty() = favoritesDao.countRecords()
}