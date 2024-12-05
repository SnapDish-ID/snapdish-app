package com.github.wisemann64.snapdishapp.ui.recipe

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.github.wisemann64.snapdishapp.R
import com.github.wisemann64.snapdishapp.databinding.ActivityRecipeBinding
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory

class RecipeActivity : AppCompatActivity() {

    companion object {
        val GONE = View.GONE
        val VISIBLE = View.VISIBLE

        fun visibility(value: Boolean): Int {
            return if (value) {
                VISIBLE
            } else {
                GONE
            }

        }
    }

    private lateinit var binding: ActivityRecipeBinding
    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = ViewModelFactory.getInstance(this.application)
        viewModel = ViewModelProvider(this@RecipeActivity,factory)[RecipeViewModel::class.java]

        supportActionBar?.hide()

        val recipeId = intent.getIntExtra("RECIPE_ID",0)

        viewModel.getRecipe(recipeId)

        binding.loadingOverlay.visibility = VISIBLE
        binding.recipeText.visibility = GONE
        binding.recipeImage.visibility = GONE
        binding.recipeTitle.visibility = GONE
        binding.backButton.visibility = GONE

        viewModel.loading.observe(this) {
            binding.loadingOverlay.visibility = visibility(it)
            binding.recipeText.visibility = visibility(!it)
            binding.recipeImage.visibility = visibility(!it)
            binding.recipeTitle.visibility = visibility(!it)
            binding.backButton.visibility = visibility(!it)
        }

        viewModel.shownRecipe.observe(this) {
            binding.recipeText.text = it.recipe
            binding.recipeTitle.text = it.title
            binding.backButton.setOnClickListener {
                finish()
            }
        }


    }
}