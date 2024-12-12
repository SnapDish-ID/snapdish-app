package com.github.wisemann64.snapdishapp.ui.snap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.github.wisemann64.snapdishapp.R
import com.github.wisemann64.snapdishapp.databinding.ActivityLoginBinding
import com.github.wisemann64.snapdishapp.databinding.ActivitySnapBinding
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory
import com.github.wisemann64.snapdishapp.ui.confirmation.ConfirmationActivity
import com.github.wisemann64.snapdishapp.ui.login.LoginViewModel

class SnapActivity : AppCompatActivity() {

    companion object {
        private const val SNAP = 0
    }

    private lateinit var binding: ActivitySnapBinding
    private lateinit var viewModel: SnapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySnapBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this.application)
        viewModel = ViewModelProvider(this@SnapActivity,factory)[SnapViewModel::class.java]

        intent.getStringExtra("on_start")?.let {
            if (it == "capture") {
                val photoUri: Uri = viewModel.createImageUri(this)
                cameraLauncher.launch(photoUri)
            } else if (it == "gallery") {
                pickImageLauncher.launch("image/*")
            }
        }

        supportActionBar?.hide()

        viewModel.picture.observe(this) {
            it?.let {
                binding.imageDisplay.setImageURI(null)
                binding.imageDisplay.setImageURI(it)
            }
        }

        binding.buttonNext.setOnClickListener {
            if (viewModel.picture.value != null) {
                val intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra("PAGE", SNAP)
                intent.putExtra("URI",viewModel.picture.value.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this,"Silakan ambil foto dari kamera atau galeri terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCamera.setOnClickListener {
            val photoUri: Uri = viewModel.createImageUri(this)
            cameraLauncher.launch(photoUri)
        }

        binding.buttonGallery.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            viewModel.setPictureUri(it)
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            viewModel.executeUriChange()
        }
    }
}