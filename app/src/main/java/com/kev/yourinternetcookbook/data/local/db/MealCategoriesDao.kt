package com.kev.yourinternetcookbook.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kev.yourinternetcookbook.data.remote.models.MealCategoriesItem

@Dao
interface MealCategoriesDao {
	@Query("SELECT * FROM categories")
	 fun getAllRecords():LiveData<List<MealCategoriesItem>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertRecords(mealCategoriesItem: MealCategoriesItem) : Long

	@Query("SELECT * FROM categories")
	suspend fun countRecords() : List<MealCategoriesItem>


}