package com.github.wisemann64.snapdishapp.tools

import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.github.wisemann64.snapdishapp.data.DataRecipeDetailed
import kotlinx.coroutines.delay

class Simulations {

    companion object {
        suspend fun simulateRecipeDataGetter(recipeId: Int): DataRecipeDetailed {
            delay(1000)
            return DataRecipeDetailed(recipeId,"nulllsss","Babi Guling Enak $recipeId","resep resep resep blablabla")
        }

        suspend fun simulateComVisInference(): List<String> {
            delay(2000)
            return listOf("wisam","leo","richard","toto","nasha","kenny")
        }

        suspend fun simulateRecommendation(recipes: List<String>): List<DataRecipe> {
            delay(3678)
            return listOf(DataRecipe(0,"","Gule"),DataRecipe(0,"","Ayam"),DataRecipe(0,"","Babi Guling"))
        }
    }

}