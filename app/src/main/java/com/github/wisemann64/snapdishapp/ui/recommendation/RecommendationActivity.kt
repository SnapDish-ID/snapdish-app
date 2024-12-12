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
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.GONE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.VISIBLE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.visibility
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory
import com.github.wisemann64.snapdishapp.ui.items.CustomLinearLayoutManager
import com.github.wisemann64.snapdishapp.ui.items.ListRecipeAdapter
import com.github.wisemann64.snapdishapp.ui.items.OnClickListener
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = ViewModelFactory.getInstance(this.application)
        viewModel = ViewModelProvider(this@RecommendationActivity,factory)[RecommendationViewModel::class.java]

        supportActionBar?.hide()

        binding.loadingOverlay.visibility = VISIBLE
        binding.recommendedRecipes.visibility = GONE

        binding.buttonHome.setOnClickListener {
            navigateBackToHome()
        }

        binding.recommendedRecipes.layoutManager = CustomLinearLayoutManager(applicationContext)

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

        viewModel.recommend(intent.getStringArrayListExtra("INGREDIENTS")?: throw Exception("Null ingredients"))
    }

    private fun navigateBackToHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
        finish()
    }
}