package com.raion.foodney

import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.raion.foodney.databinding.ActivityMainBinding
import com.raion.foodney.ui.mainScreens.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val deviceQLater = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.bottomNavBar.itemIconTintList = null
        val fragmentManager = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = fragmentManager.navController
        binding.bottomNavBar.setupWithNavController(navController)


        if (!foregroundAndBackgroundLocationPermissionApproved()) {
            val permissionArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

            requestPermissionLauncher.launch(permissionArray)
        }
    }

    @TargetApi(29)
    private fun foregroundAndBackgroundLocationPermissionApproved(): Boolean {
        val foregroundPermission = (ContextCompat.checkSelfPermission(
            baseContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
        val backgroundPermission = if (deviceQLater) {
            ContextCompat.checkSelfPermission(
                baseContext,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else true

        return foregroundPermission && backgroundPermission
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions.entries.all {
            Log.d(TAG, it.key + " value : " + it.value.toString())
            it.value
        }
        if (isGranted) {
            Log.d(TAG, "On request permission result Approved")
        } else {
            // Permission Denied
            Log.d(TAG, "Asking Permission Denied")
            Snackbar.make(
                binding.root,
                R.string.permission_denied_explanation,
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(
                    R.string.settings
                ) {
                    startActivity(
                        Intent().apply {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                    )
                }
                .show()
        }
    }
}

private const val TAG = "MainActivity"