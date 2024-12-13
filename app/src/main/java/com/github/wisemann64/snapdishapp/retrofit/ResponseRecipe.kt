package com.github.wisemann64.snapdishapp.retrofit

import com.google.gson.annotations.SerializedName

data class ResponseRecipe(

	@field:SerializedName("data")
	val data: RecipeData,

	@field:SerializedName("message")
	val message: String
)

data class RecipeData(

	@field:SerializedName("ingredients")
	val ingredients: List<String>,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("steps")
	val steps: List<String>
)
