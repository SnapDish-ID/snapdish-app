package com.github.wisemann64.snapdishapp.retrofit

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("status")
	val status: String
)

data class DataItem(

	@field:SerializedName("recipe_id")
	val recipeId: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("category")
	val category: String
)
