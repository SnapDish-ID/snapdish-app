package com.github.wisemann64.snapdishapp.tools

import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.github.wisemann64.snapdishapp.data.DataRecipeDetailed
import kotlinx.coroutines.delay

class Simulations {

    companion object {
        suspend fun simulateRecipeDataGetter(): DataRecipeDetailed {
            delay(1000)
            return DataRecipeDetailed(0,"nulllsss","Babi Guling Enak","resep resep resep blablabla")
        }
    }

}