package com.github.wisemann64.snapdishapp.data.auth

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)
