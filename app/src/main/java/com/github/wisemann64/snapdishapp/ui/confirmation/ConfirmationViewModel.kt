package com.github.wisemann64.snapdishapp.ui.confirmation

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.wisemann64.snapdishapp.data.DataRecipeDetailed
import com.github.wisemann64.snapdishapp.tools.ClassifierHelper
import com.github.wisemann64.snapdishapp.tools.Simulations
import kotlinx.coroutines.launch

class ConfirmationViewModel(private val mApplication: Application) : ViewModel() {

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

    private val _selectedMainIngredient = MutableLiveData<String>().apply {
        value = ""
    }

    val selectedMainIngredient: LiveData<String> = _selectedMainIngredient

    private val _selectorVisible  = MutableLiveData<Boolean>().apply {
        value = false
    }

    val selectorVisible: LiveData<Boolean> = _selectorVisible

    fun inference(uri: Uri) {
        _loading.value = true
        val classifier = ClassifierHelper.getInstance(mApplication)
        val result = classifier.inference(uri)
        _listItem.value = result
        _loading.value = false
    }

    fun setIngredients(ingredients: List<String>) {
        _listItem.value = ingredients
        _loading.value = false
    }

    fun setMainIngredient(mainIngredient: String) {
        _selectedMainIngredient.value = mainIngredient
    }

    fun setSelectorVisibility(value: Boolean) {
        _selectorVisible.value = value
    }

    fun toggleVisibility() {
        setSelectorVisibility(!(selectorVisible.value?:false))
    }
}