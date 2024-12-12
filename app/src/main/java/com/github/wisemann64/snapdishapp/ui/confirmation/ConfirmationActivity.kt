package com.github.wisemann64.snapdishapp.ui.confirmation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.wisemann64.snapdishapp.databinding.ActivityConfirmationBinding
import com.github.wisemann64.snapdishapp.tools.Simulations
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.GONE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.VISIBLE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.visibility
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory
import com.github.wisemann64.snapdishapp.ui.items.CustomLinearLayoutManager
import com.github.wisemann64.snapdishapp.ui.items.ListMainIngredientAdapter
import com.github.wisemann64.snapdishapp.ui.items.ListStringAdapter
import com.github.wisemann64.snapdishapp.ui.items.OnClickListenerString
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

        val factory = ViewModelFactory.getInstance(this.application)
        viewModel = ViewModelProvider(this@ConfirmationActivity,factory)[ConfirmationViewModel::class.java]

        supportActionBar?.hide()

        binding.loadingOverlay.visibility = VISIBLE
        binding.ingredientsList.visibility = GONE
        binding.recommendButton.visibility = GONE
        binding.mainIngredientSelector.visibility = GONE

        binding.ingredientsList.layoutManager = LinearLayoutManager(this)

        binding.imageFilter.setOnClickListener {
            viewModel.toggleVisibility()
        }

        binding.mainIngredient.setOnClickListener {
            viewModel.toggleVisibility()
        }

        binding.mainIngredientSelector.layoutManager = CustomLinearLayoutManager(applicationContext)

        val mainAdapter = ListMainIngredientAdapter()
        mainAdapter.submitList(Simulations.mainIngredients)
        binding.mainIngredientSelector.adapter = mainAdapter
        mainAdapter.setOnClickListener(object: OnClickListenerString {
            override fun onClick(position: Int, value: String) {
                viewModel.setMainIngredient(value)
                viewModel.setSelectorVisibility(false)
            }
        })

        viewModel.selectedMainIngredient.observe(this) {
            binding.mainIngredient.text = if (it != "") it else "Pilih bahan utama"
        }

        viewModel.loading.observe(this) {
            binding.loadingOverlay.visibility = visibility(it)
            binding.ingredientsList.visibility = visibility(!it)
            binding.recommendButton.visibility = visibility(!it)
        }

        viewModel.selectorVisible.observe(this) {
            binding.mainIngredientSelector.visibility = visibility(it)
        }

        viewModel.listItem.observe(this) { list ->
            binding.ingredientsList.adapter = ListStringAdapter(list)
            binding.recommendButton.setOnClickListener {_ ->
                if ((viewModel.selectedMainIngredient.value ?: "") == "") {
                    Toast.makeText(this, "Isi bahan utama terlebih dahulu", Toast.LENGTH_SHORT).show()
                } else {
                    val recommendationIntent = Intent(this@ConfirmationActivity,RecommendationActivity::class.java)
                    recommendationIntent.putStringArrayListExtra("INGREDIENTS", list.toCollection(ArrayList()))
                    recommendationIntent.putExtra("MAIN_INGREDIENT", viewModel.selectedMainIngredient.value ?: "ayam")
                    startActivity(recommendationIntent)
                }
            }
        }

        val previousPage = intent.getIntExtra("PAGE",-1)

        when (previousPage) {
            SNAP -> {
                val uri = Uri.parse(intent.getStringExtra("URI"))
                viewModel.inference(uri)
            }
            LIST -> {
                val ingredients = intent.getStringArrayListExtra("RECIPES")
                Log.i("ConfirmationActivity", ingredients.toString())
                ingredients?.let { viewModel.setIngredients(it) }
            }
            else -> {
                throw Exception("PAGE intent extra doesn't exist")
            }
        }
    }
}