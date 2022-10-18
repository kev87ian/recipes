package com.kev.yourinternetcookbook.data.remote.models

import com.google.gson.annotations.SerializedName

data class MealCategoriesResponse(
	@SerializedName("categories")
	val categories: List<MealCategoriesItem>
)
