package com.raion.foodney.ui.mainScreens.scanCamera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.raion.foodney.BuildConfig
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentCameraBinding
import com.raion.foodney.ui.mainScreens.MainViewModel
import com.raion.foodney.ui.mainScreens.coupons.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var codeScanner: CodeScanner
    private var scanCode = 0

    private lateinit var bottomSheet: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel


        val missionId = requireArguments().getString("id")!!
        scanCode = requireArguments().getString("code", "0").toInt()

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility = View.GONE

        if (missionId != "none") {
            viewModel.getCurrentMission(missionId)
        }

        setupBottomSheet()

        return binding.root
    }

    private fun setupBottomSheet() {
        bottomSheet = BottomSheetBehavior.from(binding.scanBottomSheet).apply {
            isDraggable = false
            isFitToContents = false
            state = BottomSheetBehavior.STATE_EXPANDED
            peekHeight = 0

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        if (this@CameraFragment::codeScanner.isInitialized) {
                            codeScanner.startPreview()
                        } else {
                            requestCameraPermission()
                        }
                    } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        stopCamera()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }

        if (scanCode == 1) {
            binding.ivScan.visibility = View.INVISIBLE
            binding.tvScanMissionObjectiveSuccess.visibility = View.VISIBLE
            binding.btnScan.text = getString(R.string.back_to_home)
            binding.tvScanMissionObjective.text = getString(R.string.success)
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED

            binding.btnScan.setOnClickListener {
                findNavController().navigate(R.id.action_cameraFragment_to_homeFragment)
            }
        } else {
            binding.btnScan.setOnClickListener {
                Toast.makeText(requireContext(), "Scan the QR Code", Toast.LENGTH_SHORT).show()
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        binding.scanBottomSheet.visibility = View.VISIBLE
    }

    private fun showSuccessScan() {
        setupBottomSheet()
    }

    private fun stopCamera() {
        if (this::codeScanner.isInitialized) {
            codeScanner.releaseResources()
        }
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
        } else {
            startScanning()
        }
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
            startScanning()
        } else {
            // Permission Denied
            Log.d(TAG, "Asking Permission Denied")
            Snackbar.make(
                binding.fragmentScanCamera,
                "Akses Kamera Dibutuhkan untuk melakukan pemindaian",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(
                    "Coba Lagi"
                ) {
                    requestCameraPermission()
                }
                .show()
        }
    }

    private fun startScanning() {
        val scannerView: CodeScannerView = binding.viewFinder
        codeScanner = CodeScanner(requireContext(), scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            lifecycleScope.launch(Dispatchers.Main) {
                if (it.text.equals("https://www.foodney.com/finish-scan/1")) {
                    viewModel.claimReward(viewModel.currentMission.value!!)
                    scanCode = 1
                    showSuccessScan()
                } else {
                    Toast.makeText(requireContext(), "QR Code Tidak Valid", Toast.LENGTH_SHORT).show()
                }
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            lifecycleScope.launch(Dispatchers.Main) {
                Toast.makeText(
                    requireContext(),
                    "Inisiasi kamera gagal: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        super.onPause()
        stopCamera()
    }
}

private const val TAG = "CameraFragment"