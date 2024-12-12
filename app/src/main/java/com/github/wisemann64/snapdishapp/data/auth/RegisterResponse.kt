package com.github.wisemann64.snapdishapp.data.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("userData")
    val userData: UserPublicData,

    @field:SerializedName("token")
    val token: String
)
