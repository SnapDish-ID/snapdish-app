package com.github.wisemann64.snapdishapp.ui.confirmation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.wisemann64.snapdishapp.R
import com.github.wisemann64.snapdishapp.databinding.ActivityConfirmationBinding
import com.github.wisemann64.snapdishapp.databinding.ActivityRecipeBinding
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.VISIBLE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.GONE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.visibility
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory
import com.github.wisemann64.snapdishapp.ui.items.ListStringAdapter
import com.github.wisemann64.snapdishapp.ui.recipe.RecipeViewModel
import com.github.wisemann64.snapdishapp.ui.recommendation.RecommendationActivity

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding
    private lateinit var viewModel: ConfirmationViewModel

    companion object {
        private const val SNAP = 0
        private const val LIST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfirmationBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = ViewModelFactory.getInstance(this.application)
        viewModel = ViewModelProvider(this@ConfirmationActivity,factory)[ConfirmationViewModel::class.java]

        supportActionBar?.hide()

        binding.loadingOverlay.visibility = VISIBLE
        binding.ingredientsList.visibility = GONE
        binding.recommendButton.visibility = GONE
        binding.backButton.visibility = GONE

        binding.ingredientsList.layoutManager = LinearLayoutManager(this)
        val adapter = ListStringAdapter(listOf("item 1","resep 2","bahan 3"," bahan 4"))
        binding.ingredientsList.adapter = adapter

        viewModel.loading.observe(this) {
            binding.loadingOverlay.visibility = visibility(it)
            binding.ingredientsList.visibility = visibility(!it)
            binding.recommendButton.visibility = visibility(!it)
            binding.backButton.visibility = visibility(!it)
        }

        viewModel.listItem.observe(this) { list ->
            binding.ingredientsList.adapter = ListStringAdapter(list)
            binding.backButton.setOnClickListener {
                finish()
            }
            binding.recommendButton.setOnClickListener {_ ->
                val recommendationIntent = Intent(this@ConfirmationActivity,RecommendationActivity::class.java)
                recommendationIntent.putStringArrayListExtra("INGREDIENTS", list.toCollection(ArrayList()))
                startActivity(recommendationIntent)
            }
        }

        val previousPage = intent.getIntExtra("PAGE",-1)

        if (previousPage == SNAP) {
            val uri = Uri.parse(intent.getStringExtra("URI"))
//            UDAH ADA URI NYA TINGGAL INFERENSI
            viewModel.inference(uri)
        } else if (previousPage == LIST) {
//            shows list
        } else {
            throw Exception("PAGE intent extra doesn't exist")
        }
    }
}