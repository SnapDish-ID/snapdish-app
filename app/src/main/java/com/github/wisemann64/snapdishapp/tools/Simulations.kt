package com.github.wisemann64.snapdishapp.tools

import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.github.wisemann64.snapdishapp.data.DataRecipeDetailed
import com.github.wisemann64.snapdishapp.retrofit.RecipeData
import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.random.Random

class Simulations {

    companion object {

        val mainIngredients: List<String> = listOf("ayam", "ikan", "kambing", "sapi", "tahu", "telur", "tempe", "udang")

        suspend fun simulateRecipeDataGetter(recipeId: String): RecipeData {
            delay(1000)
            return RecipeData(listOf("ayam","ikan","kambing","telur"),"telur","Ayam Ikan Telur", listOf("lorem ipsum 123","kukus kukus kukus kukus kukus kukus kukus kukus kukus kukus kukus kukus kukus kukus kukus kukus kukus kukus ","jadiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii iiiiiiiiiiiiiiiiiiiiiiiiiiiii iiiiiiiiiiiiiiiiiiiiiii iiiiiiiiiiiiiiiiiiiiiii iiiiiiiiiiiiiiiiiiii"))
        }
    }

}