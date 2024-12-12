package com.github.wisemann64.snapdishapp.data

import android.content.Context
import android.util.Log

internal class DataPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "data_pref"

        private const val LOGGED_IN = "logged_in"
        private const val LOGIN_SESSION = "login_session"

        private fun toRefKey(pos: Int) = "RECIPE_$pos"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun setRecipe(pos: Int, id: String) {
        val editor = preferences.edit()
        val key = toRefKey(pos)
        editor.putString(key,id)
        editor.apply()
    }

    private fun getRecipe(pos: Int): String {
        val key = toRefKey(pos)
        return preferences.getString(key,null) ?: ""
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
        val recent: ArrayList<String> = getRecentRecipes()
        Log.i("DataPreferences","recent: $recent")
        val editor = preferences.edit()

        recent.remove(id)
        recent.add(0,id)

        if (recent.size > 5) recent.removeAt(5)

        for (i in 0..4) {
            editor.putString(toRefKey(i), recent[i])
        }

        editor.apply()
    }

    fun getRecentRecipes(): ArrayList<String> {
        val list = arrayListOf(getRecipe(0),getRecipe(1),getRecipe(2),getRecipe(3),getRecipe(4))
        Log.i("DataPreferences",list.toString())
        return list
    }
}