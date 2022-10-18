package com.kev.yourinternetcookbook.data.remote.models


import com.google.gson.annotations.SerializedName

data class MealsUnderCategoryResponse(
    @SerializedName("meals")
    val meals: List<Meal>
)