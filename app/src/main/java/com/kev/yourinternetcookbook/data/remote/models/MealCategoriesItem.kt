package com.kev.yourinternetcookbook.data.remote.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "categories")
data class MealCategoriesItem(

	@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "primaryKey")
	val pk:Int,

	@ColumnInfo(name = "idCategory")
	@SerializedName("idCategory")
	val idCategory: String,

	@ColumnInfo(name = "strCategory")
	@SerializedName("strCategory")
	val strCategory: String,

	@ColumnInfo(name = "strCategoryThumb")
	@SerializedName("strCategoryThumb")
	val strCategoryThumb: String,

	@ColumnInfo(name = "strCategoryDescription")
	@SerializedName("strCategoryDescription")
	val strCategoryDescription: String
)
