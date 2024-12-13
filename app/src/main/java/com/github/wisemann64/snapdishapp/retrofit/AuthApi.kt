package com.github.wisemann64.snapdishapp.retrofit

import com.github.wisemann64.snapdishapp.data.auth.LoginRequest
import com.github.wisemann64.snapdishapp.data.auth.LoginResponse
import com.github.wisemann64.snapdishapp.data.auth.RegisterRequest
import com.github.wisemann64.snapdishapp.data.auth.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login/")
    fun login(@Body loginBody: LoginRequest): Call<LoginResponse>

    @POST("auth/register/")
    fun register(@Body registerBody: RegisterRequest): Call<RegisterResponse>
}