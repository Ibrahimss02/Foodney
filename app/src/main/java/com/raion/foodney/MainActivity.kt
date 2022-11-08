package com.raion.foodney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.raion.foodney.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavBar.itemIconTintList = null
        val fragmentManager = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = fragmentManager.navController
        binding.bottomNavBar.setupWithNavController(navController)
    }
}