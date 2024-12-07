package com.github.wisemann64.snapdishapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.github.wisemann64.snapdishapp.databinding.FragmentHomeBinding
import com.github.wisemann64.snapdishapp.ui.items.CustomLinearLayoutManager
import com.github.wisemann64.snapdishapp.ui.items.ListRecipeAdapter
import com.github.wisemann64.snapdishapp.ui.items.OnClickListener
import com.github.wisemann64.snapdishapp.ui.recipe.RecipeActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get(): FragmentHomeBinding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv: RecyclerView = binding.recentRecipes
        val adapter = ListRecipeAdapter()
        val list = mutableListOf(DataRecipe(0,"a","Rsep 1 gacor bgt wooh aku suka!!!"),
            DataRecipe(1,"b","Rsep 2 ga enakkkk"),
            DataRecipe(2,"c","Rsep 3 bolelaaa mayan enakk"))
        adapter.submitList(list)
        rv.adapter = adapter

        adapter.setOnClickListener(object: OnClickListener {
            override fun onClick(position: Int, data: DataRecipe) {
                val recipeIntent = Intent(requireActivity(), RecipeActivity::class.java)
                recipeIntent.putExtra("RECIPE_ID", data.id)
                startActivity(recipeIntent)
            }

        })

        rv.layoutManager = CustomLinearLayoutManager(requireContext())
        val head = "Mau Masak apa Hari Ini?"
        binding.textHome.text = head
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}