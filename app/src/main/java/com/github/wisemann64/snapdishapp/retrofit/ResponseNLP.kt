package com.github.wisemann64.snapdishapp.retrofit

import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.google.gson.annotations.SerializedName

data class ResponseNLP(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("status")
	val status: String
) {
	fun toDataRecipeList(): List<DataRecipe> {
		return data.map {
			DataRecipe(it.recipeId,it.title)
		}
	}
}

data class DataItem(

	@field:SerializedName("recipe_id")
	val recipeId: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("category")
	val category: String
)
