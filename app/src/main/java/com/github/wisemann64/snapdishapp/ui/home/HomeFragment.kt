package com.github.wisemann64.snapdishapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.github.wisemann64.snapdishapp.databinding.FragmentHomeBinding
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.GONE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.VISIBLE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.visibility
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory
import com.github.wisemann64.snapdishapp.ui.items.CustomLinearLayoutManager
import com.github.wisemann64.snapdishapp.ui.items.ListRecipeAdapter
import com.github.wisemann64.snapdishapp.ui.items.OnClickListener
import com.github.wisemann64.snapdishapp.ui.recipe.RecipeActivity
import com.github.wisemann64.snapdishapp.ui.snap.SnapActivity

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get(): FragmentHomeBinding = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(),factory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recentRecipes.visibility = GONE
        binding.recentLoading.visibility = VISIBLE

        binding.recentRecipes.layoutManager = CustomLinearLayoutManager(requireContext())

        binding.snapButton.setOnClickListener {
            val intent = Intent(requireActivity(),SnapActivity::class.java)
            intent.putExtra("on_start","capture")
            startActivity(intent)
        }

        binding.galleryButton.setOnClickListener {
            val intent = Intent(requireActivity(),SnapActivity::class.java)
            intent.putExtra("on_start","gallery")
            startActivity(intent)
        }

        viewModel.loading.observe(requireActivity()) {
            binding.recentRecipes.visibility = visibility(!it)
            binding.recentLoading.visibility = visibility(it)
        }

        viewModel.recent.observe(requireActivity()) {
            val adapter = ListRecipeAdapter()
            adapter.submitList(it)

            adapter.setOnClickListener(object: OnClickListener {
                override fun onClick(position: Int, data: DataRecipe) {
                    val recipeIntent = Intent(requireActivity(), RecipeActivity::class.java)
                    recipeIntent.putExtra("RECIPE_ID", data.id)
                    startActivity(recipeIntent)
                }
            })

            binding.recentRecipes.adapter = adapter
        }

        viewModel.loadRecentRecipes()
    }

    override fun onResume() {
        super.onResume()
        binding.recentRecipes.visibility = GONE
        binding.recentLoading.visibility = VISIBLE
        viewModel.loadRecentRecipes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}