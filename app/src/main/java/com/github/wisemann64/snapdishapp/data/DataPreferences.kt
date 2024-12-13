package com.github.wisemann64.snapdishapp.data

import android.content.Context
import android.util.Log

internal class DataPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "data_pref"

        private const val LOGGED_IN = "logged_in"
        private const val LOGIN_SESSION = "login_session"

        private fun toRefKey(pos: Int) = "RECIPE_$pos"
        private fun toRefKeyTitle(pos: Int) = "RECIPE_TITLE_$pos"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun setRecipe(pos: Int, id: String, title: String) {
        val editor = preferences.edit()
        val keyId = toRefKey(pos)
        val keyTitle = toRefKeyTitle(pos)
        editor.putString(keyId,id)
        editor.putString(keyTitle,title)
        editor.apply()
    }

    private fun getRecipeId(pos: Int): String {
        val key = toRefKey(pos)
        return preferences.getString(key,null) ?: ""
    }

    private fun getRecipeTitle(pos: Int): String {
        val key = toRefKeyTitle(pos)
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

    fun addRecentRecipe(recipe: DataRecipe) {
        val id = recipe.id
        val title = recipe.title

        val recent = getRecentRecipes()

        Log.i("felix felix 3",recent.toString())

        val recentId = ArrayList<String>().apply { addAll(recent.map { it.first }) }
        val recentTitle = ArrayList<String>().apply { addAll(recent.map { it.second }) }
        Log.i("DataPreferences","recent: $recent")
        val editor = preferences.edit()

        recentId.remove(id)
        recentTitle.remove(title)

        recentId.add(0,id)
        recentTitle.add(0,title)

        if (recent.size > 5) {
            recentId.removeAt(5)
            recentTitle.removeAt(5)
        }

        for (i in 0..4) {
            editor.putString(toRefKey(i), recentId[i])
            editor.putString(toRefKeyTitle(i), recentTitle[i])
        }

        editor.apply()
    }

    fun getRecentRecipes(): ArrayList<Pair<String,String>> {
        val list = arrayListOf(
            Pair(getRecipeId(0),getRecipeTitle(0)),
            Pair(getRecipeId(1),getRecipeTitle(1)),
            Pair(getRecipeId(2),getRecipeTitle(2)),
            Pair(getRecipeId(3),getRecipeTitle(3)),
            Pair(getRecipeId(4),getRecipeTitle(4))
        )
        Log.i("DataPreferences",list.toString())
        return list
    }
}