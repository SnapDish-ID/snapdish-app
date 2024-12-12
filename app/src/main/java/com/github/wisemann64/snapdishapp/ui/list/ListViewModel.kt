package com.github.wisemann64.snapdishapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {

    companion object {
        private const val TAG = "ListViewModel"
    }

    private val _recipeCount = MutableLiveData<Int>().apply {
        value = 1
    }

    val recipeCount: LiveData<Int> = _recipeCount

    private val recipes: ArrayList<String> = arrayListOf("")

    fun getRecipeList(): ArrayList<String> {
        return recipes.toCollection(ArrayList())
    }

    fun addItem() {
        recipes.add("")
        _recipeCount.value = recipes.size
    }

    fun setRecipe(position: Int, text: String) {
        recipes[position] = text
    }

    fun removeItem(position: Int) {
        if (recipes.size == 1) {
            return
        }

        recipes.removeAt(position)
        _recipeCount.value = recipes.size
    }

    fun clear() {
        recipes.clear()
        recipes.add("")
        _recipeCount.value = recipes.size
    }
}