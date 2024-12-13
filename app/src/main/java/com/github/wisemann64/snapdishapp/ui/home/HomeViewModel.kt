package com.github.wisemann64.snapdishapp.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.wisemann64.snapdishapp.data.DataPreferences
import com.github.wisemann64.snapdishapp.data.DataRecipe
import kotlinx.coroutines.runBlocking

class HomeViewModel(private val mApplication: Application) : ViewModel() {

    private val _recent = MutableLiveData<List<DataRecipe>>().apply {
        value = mutableListOf()
    }
    val recent: LiveData<List<DataRecipe>> = _recent

    private val _loading = MutableLiveData<Boolean>().apply {
        value = true
    }

    val loading: LiveData<Boolean> = _loading

    fun loadRecentRecipes() {
        _loading.value = true

        val pref = DataPreferences(mApplication)
        val recent = pref.getRecentRecipes()

        _recent.value = recent.map { DataRecipe(it.first,it.second) }

        _loading.value = false
    }
}