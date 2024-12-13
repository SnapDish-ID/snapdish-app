package com.github.wisemann64.snapdishapp.ui.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.wisemann64.snapdishapp.R
import com.github.wisemann64.snapdishapp.databinding.ActivityRecipeBinding
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.VISIBLE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.GONE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.visibility
import com.github.wisemann64.snapdishapp.ui.items.CustomLinearLayoutManager
import com.github.wisemann64.snapdishapp.ui.items.ListRecipeIngredientsAdapter
import com.github.wisemann64.snapdishapp.ui.items.ListRecipeStepsAdapter

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this.application)
        viewModel = ViewModelProvider(this@RecipeActivity,factory)[RecipeViewModel::class.java]

        supportActionBar?.hide()

        val recipeId = intent.getStringExtra("RECIPE_ID") ?: ""

        viewModel.getRecipe(recipeId,this)

        binding.loadingOverlay.visibility = VISIBLE
        binding.recipeTitle.visibility = GONE
        binding.recipeCategory.visibility = GONE
        binding.recipeIngredients.visibility = GONE
        binding.recipeSteps.visibility = GONE
        binding.textBahan.visibility = GONE
        binding.textCategory.visibility = GONE
        binding.textLangkah.visibility = GONE

        binding.recipeIngredients.layoutManager = CustomLinearLayoutManager(this)
        binding.recipeSteps.layoutManager = LinearLayoutManager(this)

        viewModel.loading.observe(this) {
            binding.loadingOverlay.visibility = visibility(it)
            binding.recipeTitle.visibility = visibility(!it)
            binding.recipeCategory.visibility = visibility(!it)
            binding.recipeIngredients.visibility = visibility(!it)
            binding.recipeSteps.visibility = visibility(!it)
            binding.textBahan.visibility = visibility(!it)
            binding.textCategory.visibility = visibility(!it)
            binding.textLangkah.visibility = visibility(!it)
        }

        viewModel.shownRecipe.observe(this) {
            binding.recipeTitle.text = it.title
            binding.recipeCategory.text = it.category
            val adapter1 = ListRecipeIngredientsAdapter()
            adapter1.submitList(it.ingredients)
            binding.recipeIngredients.adapter = adapter1

            val adapter2 = ListRecipeStepsAdapter()
            adapter2.submitList(it.steps)
            binding.recipeSteps.adapter = adapter2
        }
    }
}