package com.github.wisemann64.snapdishapp.ui.confirmation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.wisemann64.snapdishapp.data.DataRecipeDetailed
import com.github.wisemann64.snapdishapp.tools.Simulations
import kotlinx.coroutines.launch

class ConfirmationViewModel : ViewModel() {

    companion object {
        private const val TAG = "ConfirmationViewModel"
    }

    private val _loading = MutableLiveData<Boolean>().apply {
        value = true
    }

    val loading: LiveData<Boolean> = _loading

    private val _listItem = MutableLiveData<List<String>>().apply {
        value = listOf()
    }

    val listItem: LiveData<List<String>> = _listItem

    fun inference(uri: Uri) {
        _loading.value = true

//        SIMULATE MODEL INFERENCE
        viewModelScope.launch {
            val result = Simulations.simulateComVisInference()
            _listItem.value = result
            _loading.value = false
        }
    }

    fun setIngredients(ingredients: List<String>) {
        _listItem.value = ingredients
        _loading.value = false
    }
}