package com.github.wisemann64.snapdishapp.ui.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.github.wisemann64.snapdishapp.tools.Simulations
import kotlinx.coroutines.launch

class RecommendationViewModel : ViewModel() {

    companion object {
        private const val TAG = "RecommendationViewModel"
    }

    private val _loading = MutableLiveData<Boolean>().apply {
        value = true
    }

    val loading: LiveData<Boolean> = _loading

    private val _recipes = MutableLiveData<List<DataRecipe>>().apply {
        value = listOf()
    }

    val recipes: LiveData<List<DataRecipe>> = _recipes

    fun recommend(recipes: List<String>) {
        _loading.value = true

//        SIMULATE MODEL INFERENCE
        viewModelScope.launch {
            val result = Simulations.simulateRecommendation(recipes)
            _recipes.value = result
            _loading.value = false
        }
    }

}