package com.github.wisemann64.snapdishapp.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiService {

    @POST("recipes")
    fun getRecommendations(
        @Body requestBody: RequestModelNLP
    ): Call<ResponseNLP>

}