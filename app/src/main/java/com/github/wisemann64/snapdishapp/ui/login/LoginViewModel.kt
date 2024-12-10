package com.github.wisemann64.snapdishapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
        private const val LOGIN: Int = 0
        private const val REGISTER: Int = 1
    }

    private val _page = MutableLiveData<Int>().apply {
        value = LOGIN
    }

    val page: LiveData<Int> = _page

    private val _username = MutableLiveData<String>().apply {
        value = ""
    }
    private val _password = MutableLiveData<String>().apply {
        value = ""
    }
    private val _confirmPassword = MutableLiveData<String>().apply {
        value = ""
    }
    private val _email = MutableLiveData<String>().apply {
        value = ""
    }

    val username: LiveData<String> = _username
    val password: LiveData<String> = _password
    val confirmPassword: LiveData<String> = _confirmPassword
    val email: LiveData<String> = _email

    fun setUsername(value: String) {
        _username.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    fun setConfirmPassword(value: String) {
        _confirmPassword.value = value
    }

    fun setEmail(value: String) {
        _email.value = value
    }

    private fun clearCredentials() {
        _email.value = ""
        _username.value = ""
        _password.value = ""
        _confirmPassword.value = ""
    }

    fun setViewToRegister() {
        clearCredentials()

        _page.value = REGISTER
    }

    fun setViewToLogin() {
        clearCredentials()

        _page.value = LOGIN
    }
}