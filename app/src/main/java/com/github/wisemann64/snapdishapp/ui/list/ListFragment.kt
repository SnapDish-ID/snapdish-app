package com.github.wisemann64.snapdishapp.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.wisemann64.snapdishapp.R
import com.github.wisemann64.snapdishapp.data.DataIngredient
import com.github.wisemann64.snapdishapp.databinding.FragmentListBinding
import com.github.wisemann64.snapdishapp.databinding.FragmentSnapBinding
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory
import com.github.wisemann64.snapdishapp.ui.confirmation.ConfirmationActivity
import com.github.wisemann64.snapdishapp.ui.items.ListIngredientsAdapter
import com.github.wisemann64.snapdishapp.ui.snap.SnapFragment
import com.github.wisemann64.snapdishapp.ui.snap.SnapViewModel

class ListFragment : Fragment() {

    companion object {
        private const val LIST = 1
    }


    private lateinit var viewModel: ListViewModel
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(),factory)[ListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater,container,false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNext.setOnClickListener {
            if (viewModel.recipeCount.value != 0) {
                val intent = Intent(requireActivity(), ConfirmationActivity::class.java)
                intent.putExtra("PAGE", LIST)
                intent.putStringArrayListExtra("RECIPES",ArrayList<String>(viewModel.getRecipeList().toSet().filter { it.isNotEmpty() }))
                startActivity(intent)
            }
        }

        binding.addButton.setOnClickListener {
            viewModel.addItem()
        }

        binding.clearButton.setOnClickListener {
            viewModel.clear()
        }

        binding.ingredientsList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.recipeCount.observe(requireActivity()) {
            val adapter = ListIngredientsAdapter(this,viewModel)
            adapter.submitList(viewModel.getRecipeList().map { DataIngredient(it) })
            binding.ingredientsList.adapter = adapter
        }
    }
}