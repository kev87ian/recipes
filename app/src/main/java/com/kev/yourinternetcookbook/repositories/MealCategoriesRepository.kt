package com.kev.yourinternetcookbook.repositories

import com.kev.yourinternetcookbook.data.local.db.MealCategoriesDao
import com.kev.yourinternetcookbook.data.remote.models.MealCategoriesItem
import com.kev.yourinternetcookbook.data.remote.network.ApiService
import com.kev.yourinternetcookbook.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class MealCategoriesRepository @Inject constructor(
	private val apiService: ApiService,
	private val mealCategoriesDao: MealCategoriesDao
) {

	suspend fun fetchMealCategories(): Flow<Resource<List<MealCategoriesItem>>> = flow {
		emit(Resource.Loading())
/*		We first check if the local database has any data stored, ensuring that data displayed will always be from the local database,
		which is quicker and less resource-intensive;
		if the database is empty, we make and api call and insert the response into the database.
		if the database is empty and there's no internet connection, we post a connectivity error message to the user*/

		var categoriesInDatabase = mealCategoriesDao.countRecords()
		if (categoriesInDatabase.isEmpty()){
			val response = apiService.fetchMealCategories()
			categoriesInDatabase = response.body()?.categories!!
			for(category in categoriesInDatabase){
				mealCategoriesDao.insertRecords(category)
			}

			emit(Resource.Success(categoriesInDatabase))
		}

		emit(Resource.Success(categoriesInDatabase))

	}.flowOn(Dispatchers.IO).catch { e->
		when(e){
			is IOException -> emit(Resource.Error("Ensure you have an active internet connection"))
		}
	}


	suspend fun insertIntoDb(mealCategoriesItem: MealCategoriesItem){
		mealCategoriesDao.insertRecords(mealCategoriesItem)
	}

}