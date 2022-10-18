package com.kev.yourinternetcookbook.repositories

import com.kev.yourinternetcookbook.data.local.db.FavoritesDao
import com.kev.yourinternetcookbook.data.remote.models.Meal
import com.kev.yourinternetcookbook.data.remote.models.MealsUnderCategoryResponse
import com.kev.yourinternetcookbook.data.remote.network.ApiService
import com.kev.yourinternetcookbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MealDetailsRepository @Inject constructor(
	private val apiService: ApiService,
	private val favoritesDao: FavoritesDao
) {

	suspend fun fetchMealDetails(id:String) : Flow<Resource<MealsUnderCategoryResponse>> = flow{
		emit(Resource.Loading())
		val response = apiService.fetchMealDetails(id)
		emit(Resource.Success(response.body()!!))

	}.catch { e->

		when(e){
			is IOException -> emit(Resource.Error("Check your internet connection and retry"))
		}
	}

	suspend fun saveRecipe(meal:Meal) = favoritesDao.saveRecipe(meal)
}