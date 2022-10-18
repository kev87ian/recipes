package com.kev.yourinternetcookbook.data.remote.network

import com.kev.yourinternetcookbook.data.remote.models.MealCategoriesResponse
import com.kev.yourinternetcookbook.data.remote.models.MealsUnderCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
	@GET("categories.php")
	suspend fun fetchMealCategories() : Response<MealCategoriesResponse>

	@GET("filter.php?")
	suspend fun fetchMealsUnderCategories(@Query("c") name:String): Response<MealsUnderCategoryResponse>

	@GET("lookup.php?")
	suspend fun fetchMealDetails(@Query("i") id:String) : Response<MealsUnderCategoryResponse>

	@GET("search.php")
	suspend fun searchRecipe(@Query("s") searchQuery:String) : MealsUnderCategoryResponse
}