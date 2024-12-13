package com.github.wisemann64.snapdishapp.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("recipes")
    fun getRecommendations(
        @Body requestBody: RequestModelNLP
    ): Call<ResponseNLP>

    @GET("api/recipe/{id}")
    fun getRecipeFromId(
        @Path("id") recipeId: String
    ): Call<ResponseRecipe>
}