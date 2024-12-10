package com.github.wisemann64.snapdishapp.data

import android.content.Context

internal class DataPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "data_pref"

        private const val FIRST_RECIPE = "first"
        private const val SECOND_RECIPE = "second"
        private const val THIRD_RECIPE = "third"

        private const val LOGGED_IN = "logged_in"
        private const val LOGIN_SESSION = "login_session"

        private const val FIRST = 0
        private const val SECOND = 1
        private const val THIRD = 2
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setRecipe(pos: Int, id: String) {
        val editor = preferences.edit()
        val key = if (pos == FIRST) FIRST_RECIPE
        else if (pos == SECOND) SECOND_RECIPE
        else THIRD_RECIPE
        editor.putString(key,id)
        editor.apply()
    }

    fun getRecipe(pos: Int): String {
        val key = if (pos == FIRST) FIRST_RECIPE
        else if (pos == SECOND) SECOND_RECIPE
        else THIRD_RECIPE

        return preferences.getString(key,"") ?: ""
    }

    fun login(session: String) {
        val editor = preferences.edit()
        editor.putString(LOGIN_SESSION,session)
        editor.putBoolean(LOGGED_IN,true)
        editor.apply()
    }

    fun logout() {
        val editor = preferences.edit()
        editor.remove(LOGIN_SESSION)
        editor.putBoolean(LOGGED_IN,false)
        editor.apply()
    }

    fun getSession(): String? {
        return preferences.getString(LOGIN_SESSION,"")
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(LOGGED_IN,false)
    }

    fun addRecentRecipe(id: String) {
        val editor = preferences.edit()

        editor.putString(THIRD_RECIPE,preferences.getString(SECOND_RECIPE,""))
        editor.putString(SECOND_RECIPE,preferences.getString(FIRST_RECIPE,""))
        editor.putString(FIRST_RECIPE,id)

        editor.apply()
    }

    fun getRecentRecipes(): List<String?> {
        return listOf(
            preferences.getString(FIRST_RECIPE,null),
            preferences.getString(SECOND_RECIPE,null),
            preferences.getString(THIRD_RECIPE,null))
    }
}