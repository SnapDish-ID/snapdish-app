package com.github.wisemann64.snapdishapp.controller

import com.github.wisemann64.snapdishapp.data.auth.LoginRequest
import com.github.wisemann64.snapdishapp.data.auth.LoginResponse
import com.github.wisemann64.snapdishapp.data.auth.RegisterRequest
import com.github.wisemann64.snapdishapp.data.auth.RegisterResponse
import com.github.wisemann64.snapdishapp.retrofit.ApiConfig
import com.github.wisemann64.snapdishapp.retrofit.AuthCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class AuthController {
    private val authService = ApiConfig.getAuthService()

    fun register(email: String, name: String, password: String, callback: AuthCallback) {
        val registerRequest = RegisterRequest(email, name, password)

        return authService.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    val token = registerResponse?.token
                    token?.let { callback.onSuccess(it) } ?: callback.onFailure("Token is null")
                } else {
                    callback.onFailure("Failed to register")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                callback.onFailure(t.message ?: "Unknown error")
            }
        })
    }

    fun login(email: String, password: String, callback: AuthCallback) {
        val loginRequest = LoginRequest(email, password)

        return authService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.token
                    token?.let { callback.onSuccess(it) } ?: callback.onFailure("Token is null")
                } else {
                    throw HttpException(response)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onFailure(t.message ?: "Unknown error")
            }
        })
    }
}