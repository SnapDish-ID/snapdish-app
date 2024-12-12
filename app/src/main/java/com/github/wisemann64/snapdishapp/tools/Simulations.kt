package com.github.wisemann64.snapdishapp.tools

import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.github.wisemann64.snapdishapp.data.DataRecipeDetailed
import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.random.Random

class Simulations {

    companion object {

        private val mainIngredients: List<String> = listOf("ayam", "ikan", "kambing", "sapi", "tahu", "telur", "tempe", "udang")

        suspend fun simulateRecipeDataGetter(recipeId: String): DataRecipeDetailed {
            delay(1000)
            return DataRecipeDetailed(recipeId,"Babi Guling Enak $recipeId","resep resep resep blablabla")
        }

        suspend fun simulateComVisInference(): List<String> {
            delay(2000)
            return listOf("wisam","leo","richard","toto","nasha","kenny")
        }

        suspend fun simulateRecommendation(recipes: List<String>, main: String): List<DataRecipe> {
            delay(1000)
            return listOf(DataRecipe(UUID.randomUUID().toString(),main + "1"),DataRecipe(UUID.randomUUID().toString(),main + "2"),DataRecipe(UUID.randomUUID().toString(),main + "3"))
        }
    }

}