package com.github.wisemann64.snapdishapp.ui.favorites

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.wisemann64.snapdishapp.R
import com.github.wisemann64.snapdishapp.data.DataPreferences
import com.github.wisemann64.snapdishapp.databinding.FragmentFavoritesBinding
import com.github.wisemann64.snapdishapp.databinding.FragmentListBinding
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory
import com.github.wisemann64.snapdishapp.ui.list.ListViewModel
import com.github.wisemann64.snapdishapp.ui.login.LoginActivity

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(),factory)[FavoritesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val dataPref = DataPreferences(requireContext())
        if (dataPref.isLoggedIn()) {
            binding.notLoggedIn.visibility = View.GONE
            binding.loggedIn.visibility = View.VISIBLE

            binding.buttonLogout.setOnClickListener {
                val intent = Intent(requireActivity(),LoginActivity::class.java)
                intent.putExtra("back_to_close",true)
                startActivity(intent)
                dataPref.logout()
            }

        } else {
            binding.notLoggedIn.visibility = View.VISIBLE
            binding.loggedIn.visibility = View.GONE

            binding.buttonLogin.setOnClickListener {
                val intent = Intent(requireActivity(),LoginActivity::class.java)
                startActivity(intent)
            }
        }

    }
}