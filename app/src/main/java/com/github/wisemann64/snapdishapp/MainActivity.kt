package com.github.wisemann64.snapdishapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.github.wisemann64.snapdishapp.data.DataPreferences
import com.github.wisemann64.snapdishapp.databinding.ActivityMainBinding
import com.github.wisemann64.snapdishapp.ui.home.HomeFragment
import com.github.wisemann64.snapdishapp.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_snap, R.id.navigation_list, R.id.navigation_favorites
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportActionBar?.hide()

        if (!intent.getBooleanExtra("as_guest",false)) loginCheck()

//        DataPreferences(this).addRecentRecipe("3")
//        DataPreferences(this).addRecentRecipe("2")
//        DataPreferences(this).addRecentRecipe("1")
    }

    private fun loginCheck() {
        val pref = DataPreferences(this)
        if (!pref.isLoggedIn()) {
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            intent.putExtra("back_to_close",true)
            startActivity(intent)
        }
    }
}