package com.github.wisemann64.snapdishapp.data.auth

import com.google.gson.annotations.SerializedName

data class UserPublicData(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("name")
    val name: String
)
