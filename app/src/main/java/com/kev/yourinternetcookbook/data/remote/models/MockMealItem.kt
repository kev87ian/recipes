package com.kev.yourinternetcookbook.data.remote.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*A mock meal data class for testing purposes*/
@Entity(tableName = "mockmeals")
data class MockMealItem(
	@PrimaryKey(autoGenerate = false)
	@ColumnInfo(name = "primaryKey")
	@SerializedName("idMeal")
	val idMeal: String,

	@ColumnInfo(name = "strMealThumb")
	@SerializedName("strMealThumb")
	val strMealThumb : String?,
	@SerializedName("strArea")
	val strArea: String,
	@SerializedName("strCategory")
	val strCategory: String?
)
