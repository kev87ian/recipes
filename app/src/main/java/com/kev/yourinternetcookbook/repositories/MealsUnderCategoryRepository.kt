package com.kev.yourinternetcookbook.repositories

import com.kev.yourinternetcookbook.data.remote.models.MealsUnderCategoryResponse
import com.kev.yourinternetcookbook.data.remote.network.ApiService
import com.kev.yourinternetcookbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MealsUnderCategoryRepository @Inject constructor(
private val apiService: ApiService
) {
	suspend fun fetchMealsUnderCategory(categoryName: String): Flow<Resource<MealsUnderCategoryResponse>> =
		flow {
			emit(Resource.Loading())
			val response = apiService.fetchMealsUnderCategories(categoryName)

			emit(Resource.Success(response.body()!!))

		}.catch {e->
			when(e){
				is IOException -> emit(Resource.Error("Check your internet connection"))
			}
		}
}