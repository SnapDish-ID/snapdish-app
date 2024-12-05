package com.github.wisemann64.snapdishapp.ui.recipe

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.wisemann64.snapdishapp.data.DataRecipeDetailed
import com.github.wisemann64.snapdishapp.tools.Simulations
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application): ViewModel() {

    companion object {
        private const val TAG = "RecipeViewModel"
    }

    private val _loading = MutableLiveData<Boolean>().apply {
        value = true
    }

    private val _shownRecipe = MutableLiveData<DataRecipeDetailed>().apply {
        value = DataRecipeDetailed(-1,"","","")
    }

    val loading: LiveData<Boolean> = _loading

    val shownRecipe: LiveData<DataRecipeDetailed> = _shownRecipe

    fun getRecipe(recipeId: Int) {
        _loading.value = true

//        SIMULATE API CALLING
        viewModelScope.launch {
            val result: DataRecipeDetailed = Simulations.simulateRecipeDataGetter()
            _shownRecipe.value = result
            _loading.value = false
        }
    }

}

