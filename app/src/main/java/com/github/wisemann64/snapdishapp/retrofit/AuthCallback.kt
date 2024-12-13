package com.github.wisemann64.snapdishapp.retrofit

interface AuthCallback {
    fun onSuccess(token: String)
    fun onFailure(errorMessage: String)
}