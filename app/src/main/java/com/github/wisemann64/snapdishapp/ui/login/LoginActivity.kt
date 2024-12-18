package com.github.wisemann64.snapdishapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.github.wisemann64.snapdishapp.MainActivity
import com.github.wisemann64.snapdishapp.data.DataPreferences
import com.github.wisemann64.snapdishapp.databinding.ActivityLoginBinding
import com.github.wisemann64.snapdishapp.tools.ViewModelFactory

import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.VISIBLE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.GONE
import com.github.wisemann64.snapdishapp.tools.ToolsVisibility.Companion.visibility

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginActivity"
        private const val LOGIN: Int = 0
        private const val REGISTER: Int = 1
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this.application)
        viewModel = ViewModelProvider(this@LoginActivity,factory)[LoginViewModel::class.java]

        if (intent.getBooleanExtra("back_to_close",false)) {
            setBackToCloseApp()
        }

        supportActionBar?.hide()

        binding.editLoginUname.addTextChangedListener {
            viewModel.setUsername(it.toString())
        }

        binding.editLoginPass.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }

        binding.editRegistUname.addTextChangedListener {
            viewModel.setUsername(it.toString())
        }

        binding.editRegistEmail.addTextChangedListener {
            viewModel.setEmail(it.toString())
        }

        binding.editRegistPassword.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }

        binding.editRegistConfirmPassword.addTextChangedListener {
            viewModel.setConfirmPassword(it.toString())
        }

        binding.textLoginAsGuest.setOnClickListener {
            loginAsGuest()
        }

        loginView()

        viewModel.page.observe(this) {
            if (it == REGISTER) registerView()
            if (it == LOGIN) loginView()
        }
    }

    private fun clearRegisterCredentials() {
        binding.editRegistEmail.setText("")
        binding.editRegistUname.setText("")
        binding.editRegistPassword.setText("")
        binding.editRegistConfirmPassword.setText("")
    }

    private fun clearLoginCredentials() {
        binding.editLoginPass.setText("")
        binding.editLoginUname.setText("")
    }

    private fun loginView() {
        clearLoginCredentials()
        binding.loginHandler.visibility = VISIBLE
        binding.registerHandler.visibility = GONE
        binding.buttonLogin.setOnClickListener {  }
        binding.buttonRegister.setOnClickListener {
            viewModel.setViewToRegister()
        }
        binding.buttonRegistRegister.setOnClickListener {

        }

        binding.buttonLoginLogin.setOnClickListener {
//            Toast.makeText(this,"[${viewModel.username.value}] [${viewModel.password.value}]", Toast.LENGTH_SHORT).show()
            Toast.makeText(this,"Logged in", Toast.LENGTH_SHORT).show()

            DataPreferences(this).login("abcabc")

            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(intent)
            finish()
        }
    }

    private fun registerView() {
        clearRegisterCredentials()

        binding.loginHandler.visibility = GONE
        binding.registerHandler.visibility = VISIBLE

        binding.buttonLogin.setOnClickListener { viewModel.setViewToLogin() }
        binding.buttonRegister.setOnClickListener { }

        binding.buttonRegistRegister.setOnClickListener {
            Toast.makeText(this, "[${viewModel.email.value}] [${viewModel.username.value}] [${viewModel.password.value}] [${viewModel.confirmPassword.value}]", Toast.LENGTH_SHORT).show()
        }

        binding.buttonLoginLogin.setOnClickListener {  }
    }

    private fun loginAsGuest() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        intent.putExtra("as_guest",true)
        startActivity(intent)
        finish()
    }

    private fun setBackToCloseApp() {
        onBackPressedDispatcher.addCallback(this,object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        })
    }
}