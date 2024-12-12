package com.github.wisemann64.snapdishapp.ui.recommendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.github.wisemann64.snapdishapp.MainActivity
import com.github.wisemann64.snapdishapp.R
import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.github.wisemann64.snapdishapp.databinding.ActivityRecommendationBinding
import com.github.wisemann64.snapdishapp.tools.Simulations
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.GONE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.VISIBLE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.visibility
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory
import com.github.wisemann64.snapdishapp.ui.items.CustomLinearLayoutManager
import com.github.wisemann64.snapdishapp.ui.items.ListMainIngredientAdapter
import com.github.wisemann64.snapdishapp.ui.items.ListRecipeAdapter
import com.github.wisemann64.snapdishapp.ui.items.OnClickListener
import com.github.wisemann64.snapdishapp.ui.items.OnClickListenerString
import com.github.wisemann64.snapdishapp.ui.recipe.RecipeActivity

class RecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendationBinding
    private lateinit var viewModel: RecommendationViewModel

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecommendationBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this.application)
        viewModel = ViewModelProvider(this@RecommendationActivity,factory)[RecommendationViewModel::class.java]

        supportActionBar?.hide()

        binding.loadingOverlay.visibility = VISIBLE
        binding.recommendedRecipes.visibility = GONE
        binding.mainIngredientSelector.visibility = GONE

        binding.buttonHome.setOnClickListener {
            navigateBackToHome()
        }

        binding.imageFilter.setOnClickListener {
            viewModel.toggleVisibility()
        }

        binding.mainIngredient.setOnClickListener {
            viewModel.toggleVisibility()
        }

        binding.recommendedRecipes.layoutManager = CustomLinearLayoutManager(applicationContext)
        binding.mainIngredientSelector.layoutManager = CustomLinearLayoutManager(applicationContext)

        val mainAdapter = ListMainIngredientAdapter()
        mainAdapter.submitList(Simulations.mainIngredients)
        binding.mainIngredientSelector.adapter = mainAdapter
        mainAdapter.setOnClickListener(object: OnClickListenerString {
            override fun onClick(position: Int, value: String) {
                if (value != viewModel.main.value) {
                    viewModel.setMainIngredient(value)
                    viewModel.recommend(viewModel.ingredients,value)
                }
                viewModel.setSelectorVisibility(false)
            }
        })

        viewModel.loading.observe(this) {
            binding.loadingOverlay.visibility = visibility(it)
            binding.recommendedRecipes.visibility = visibility(!it)
        }

        viewModel.recipes.observe(this) {
            val adapter = ListRecipeAdapter()
            adapter.submitList(it)
            binding.recommendedRecipes.adapter = adapter

            adapter.setOnClickListener(object: OnClickListener {
                override fun onClick(position: Int, data: DataRecipe) {
                    val recipeIntent = Intent(this@RecommendationActivity, RecipeActivity::class.java)
                    recipeIntent.putExtra("RECIPE_ID", data.id)
                    startActivity(recipeIntent)
                }
            })
        }

        viewModel.selectorVisible.observe(this) {
            binding.mainIngredientSelector.visibility = visibility(it)
        }

        viewModel.main.observe(this) {
            binding.mainIngredient.text = it
        }

        viewModel.recommend(intent.getStringArrayListExtra("INGREDIENTS")?: throw Exception("Null ingredients")
            ,intent.getStringExtra("MAIN_INGREDIENT")?: "ayam")
    }

    private fun navigateBackToHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
        finish()
    }
}