package com.github.wisemann64.snapdishapp.ui.recommendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.github.wisemann64.snapdishapp.retrofit.ApiConfig
import com.github.wisemann64.snapdishapp.retrofit.RequestModelNLP
import com.github.wisemann64.snapdishapp.retrofit.ResponseNLP
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private val _main = MutableLiveData<String>().apply {
        value = "ayam"
    }

    val main: LiveData<String> = _main

    private val _selectorVisible  = MutableLiveData<Boolean>().apply {
        value = false
    }

    val selectorVisible: LiveData<Boolean> = _selectorVisible

    lateinit var ingredients: List<String>

    fun recommend(ingredients: List<String>, mainIngredient: String) {
        this.ingredients = ingredients

        _loading.value = true
        _main.value = mainIngredient

        val client = ApiConfig.getNLPApiService().getRecommendations(RequestModelNLP(mainIngredient,ingredients))
        client.enqueue(object : Callback<ResponseNLP> {
            override fun onResponse(call: Call<ResponseNLP>, response: Response<ResponseNLP>) {
                _loading.postValue(false)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _recipes.postValue(it.toDataRecipeList())
                    }
                } else {
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseNLP>, t: Throwable) {
                _loading.postValue(false)
                Log.e(TAG,"onFailure: ${t.message}")
            }
        })
    }

    fun setMainIngredient(mainIngredient: String) {
        _main.value = mainIngredient
    }

    fun setSelectorVisibility(value: Boolean) {
        _selectorVisible.value = value
    }

    fun toggleVisibility() {
        setSelectorVisibility(!(selectorVisible.value?:false))
    }
}