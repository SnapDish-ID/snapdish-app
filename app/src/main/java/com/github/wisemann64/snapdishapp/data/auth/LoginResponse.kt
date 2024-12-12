package com.github.wisemann64.snapdishapp.data.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("token")
    val token: String
)
