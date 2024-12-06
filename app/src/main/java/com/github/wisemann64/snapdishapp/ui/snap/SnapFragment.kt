package com.github.wisemann64.snapdishapp.ui.snap

import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.github.wisemann64.snapdishapp.R
import com.github.wisemann64.snapdishapp.databinding.FragmentHomeBinding
import com.github.wisemann64.snapdishapp.databinding.FragmentSnapBinding
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory
import com.github.wisemann64.snapdishapp.ui.confirmation.ConfirmationActivity
import com.github.wisemann64.snapdishapp.ui.recipe.RecipeViewModel

class SnapFragment : Fragment() {

    companion object {
        private const val SNAP = 0
    }

    private lateinit var viewModel: SnapViewModel
    private var _binding: FragmentSnapBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(),factory)[SnapViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSnapBinding.inflate(inflater,container,false)
        val root: View = binding.root

        return root
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            viewModel.setPictureUri(it)
        }
    }

    private val cameraLauncer = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            Log.i("SnapFragment","Camera OK!")
            viewModel.executeUriChange()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.picture.observe(requireActivity()) {
            it?.let {
                binding.imageDisplay.setImageURI(null)
                binding.imageDisplay.setImageURI(it)
            }
        }

        binding.buttonGallery.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.buttonCamera.setOnClickListener {
            val photoUri: Uri = viewModel.createImageUri(requireActivity())
            cameraLauncer.launch(photoUri)
        }

        binding.buttonNext.setOnClickListener {
            if (viewModel.picture.value != null) {
                val intent = Intent(requireActivity(),ConfirmationActivity::class.java)
                intent.putExtra("PAGE",SNAP)
                intent.putExtra("URI",viewModel.picture.value.toString())
                startActivity(intent)
            }
        }
    }
}