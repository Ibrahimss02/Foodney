package com.raion.foodney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.raion.foodney.databinding.ActivityMainBinding
import com.raion.foodney.ui.mainScreens.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.bottomNavBar.itemIconTintList = null
        val fragmentManager = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = fragmentManager.navController
        binding.bottomNavBar.setupWithNavController(navController)
    }
}