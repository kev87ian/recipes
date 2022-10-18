package com.kev.yourinternetcookbook.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kev.yourinternetcookbook.data.remote.models.Meal
import com.kev.yourinternetcookbook.data.remote.models.MockMealItem

@Dao
interface FavoritesDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun saveRecipe(meal: Meal) : Long


	@Query("SELECT * from meals")
	fun getSavedRecipes() : LiveData<List<Meal>>



	@Query("SELECT * FROM meals")
	fun countRecords() : Array<Meal>

	@Delete
	 fun deleteRecipes(meal: Meal)


	 /*The functions below mock the actual functions for testing purposes*/
	@Query("SELECT * from mockmeals")
	fun getMockRecipeItems() : LiveData<List<MockMealItem>>
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun mockSaveRecipe(mealItem: MockMealItem) : Long

	@Delete
	fun deleteMockRecipes(mockMealItem: MockMealItem)


}