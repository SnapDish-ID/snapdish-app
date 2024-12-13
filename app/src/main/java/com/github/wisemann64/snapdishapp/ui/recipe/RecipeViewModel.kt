package com.github.wisemann64.snapdishapp.ui.recipe

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.wisemann64.snapdishapp.data.DataPreferences
import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.github.wisemann64.snapdishapp.retrofit.ApiConfig
import com.github.wisemann64.snapdishapp.retrofit.RecipeData
import com.github.wisemann64.snapdishapp.retrofit.ResponseRecipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel(application: Application): ViewModel() {

    companion object {
        private const val TAG = "RecipeViewModel"
    }

    private val _loading = MutableLiveData<Boolean>().apply {
        value = true
    }

    private val _shownRecipe = MutableLiveData<RecipeData>().apply {
        value = RecipeData(listOf(),"","", listOf())
    }

    val loading: LiveData<Boolean> = _loading

    val shownRecipe: LiveData<RecipeData> = _shownRecipe

    fun getRecipe(recipeId: String, context: Context) {
        _loading.value = true
        val client = ApiConfig.getRecipesApiService().getRecipeFromId(recipeId)
        client.enqueue(object: Callback<ResponseRecipe> {

            override fun onResponse(
                call: Call<ResponseRecipe>,
                response: Response<ResponseRecipe>
            ) {
                _loading.postValue(false)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _shownRecipe.postValue(it.data)
                        Log.i("felix felix",it.data.title)
                        DataPreferences(context).addRecentRecipe(DataRecipe(recipeId,it.data.title))
                    }
                } else {
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseRecipe>, t: Throwable) {
                _loading.postValue(false)
                Log.e(TAG,"onFailure: ${t.message}")
            }
        })
    }

}

