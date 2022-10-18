package com.kev.yourinternetcookbook.repositories

import com.kev.yourinternetcookbook.data.remote.models.MealsUnderCategoryResponse
import com.kev.yourinternetcookbook.data.remote.network.ApiService
import com.kev.yourinternetcookbook.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import javax.inject.Inject

class SearchMealsRepository @Inject constructor(
	private val apiService: ApiService
) {

	suspend fun searchMeals(mealName: String): Flow<Resource<MealsUnderCategoryResponse>> = flow {
		emit(Resource.Loading())
		val response = apiService.searchRecipe(mealName)
		if (response.meals.isNullOrEmpty()) {
			emit(Resource.Error("No result(s) found."))
		} else {
			emit(Resource.Success(response))
		}


	}.retry(2) { e ->
		//the retry block of code means i wanna perform 3 tries when an exception occurs.
		//But before retrying, delay the coroutine for one second
		(e is Exception).also { if (it) delay(1000) }
	}.catch { e ->
		when (e) {
			is java.io.IOException -> emit(Resource.Error("Ensure you have an active internet connection."))
			is HttpException -> emit(Resource.Error("We're unable to communicate with servers. Please retry."))
			else -> emit(Resource.Error(e.localizedMessage?.toString()!!))
		}
	}.flowOn(Dispatchers.Default)
}
/*
	suspend fun searchMeals(mealName: String): Flow<Resource<MealsUnderCategoryResponse>> = flow {
		emit(Resource.Loading())
		val response = apiService.searchRecipe(mealName)
		emit(handleSearchResponse(response))
	}.catch { e ->
		when (e) {
			is IOException -> emit(Resource.Error("Check your internet connection"))
		}
	}

	private fun handleSearchResponse(response: Response<MealsUnderCategoryResponse>): Resource<MealsUnderCategoryResponse> {


		if (response.isSuccessful){
			response.body().let {response->
				return Resource.Success(response!!)
			}
		}

		else if (response.isSuccessful&& response.body()?.meals.isNullOrEmpty()){
			response.body().let {
				return Resource.Error("No results found")
			}

		}

		return Resource.Error(response.errorBody().toString())

	}
*/

/*
* 	emit(Resource.Loading())
		val response = apiService.searchRecipe(mealName)
		if (response.meals.isEmpty()) {
			emit(Resource.Error("No result(s) found"))
		} else {
			emit(Resource.Success(response.body()!!))
		}
	}.catch { e ->
		when (e) {
			is IOException -> emit(Resource.Error("Ensure you have an active internet connection"))
		}
*/
