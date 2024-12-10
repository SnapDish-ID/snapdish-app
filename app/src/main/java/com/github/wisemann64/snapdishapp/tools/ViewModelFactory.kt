package com.github.wisemann64.snapdishapp.tools

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.wisemann64.snapdishapp.ui.confirmation.ConfirmationViewModel
import com.github.wisemann64.snapdishapp.ui.favorites.FavoritesViewModel
import com.github.wisemann64.snapdishapp.ui.list.ListViewModel
import com.github.wisemann64.snapdishapp.ui.login.LoginViewModel
import com.github.wisemann64.snapdishapp.ui.recipe.RecipeViewModel
import com.github.wisemann64.snapdishapp.ui.recommendation.RecommendationViewModel
import com.github.wisemann64.snapdishapp.ui.snap.SnapViewModel

class ViewModelFactory private constructor(private val mApplication: Application): ViewModelProvider.NewInstanceFactory()   {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(SnapViewModel::class.java)) {
            return SnapViewModel() as T
        }
        if (modelClass.isAssignableFrom(ConfirmationViewModel::class.java)) {
            return ConfirmationViewModel() as T
        }
        if (modelClass.isAssignableFrom(RecommendationViewModel::class.java)) {
            return RecommendationViewModel() as T
        }
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel() as T
        }
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel() as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}