package com.raion.foodney.ui.mainScreens.scanCamera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentCameraBinding
import com.raion.foodney.ui.mainScreens.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var codeScanner: CodeScanner

    private lateinit var bottomSheet: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(layoutInflater)

        val missionId = requireArguments().getString("id")!!

        if (missionId != "none") {
            viewModel.getCurrentMission(missionId)
        }

        setupBottomSheet()

        return binding.root
    }

    private fun setupBottomSheet() {
        bottomSheet = BottomSheetBehavior.from(binding.scanBottomSheet).apply {
            isDraggable = false
            state = BottomSheetBehavior.STATE_EXPANDED

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        startCamera()
                    } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        stopCamera()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }

        if (viewModel.currentMission.value != null) {
            binding.ivScan.visibility = View.INVISIBLE
            binding.tvScanMissionObjectiveSuccess.visibility = View.VISIBLE
            binding.btnScan.text = getString(R.string.back_to_home)
            binding.tvScanMissionObjective.text = getString(R.string.success)

            binding.btnScan.setOnClickListener {
                findNavController().navigate(R.id.action_cameraFragment_to_homeFragment)
            }
        } else {
            binding.btnScan.setOnClickListener {
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        binding.scanBottomSheet.visibility = View.VISIBLE
    }

    private fun stopCamera() {

    }

    private fun startCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 123)
        } else {
            startScanning()
        }
    }

    private fun startScanning() {
//        val scannerView: CodeScannerView = binding.viewFinder
//        codeScanner = CodeScanner(requireContext(), scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

//        codeScanner.decodeCallback = DecodeCallback {
//            lifecycleScope.launch(Dispatchers.Main) {
//                Toast.makeText(
//                    this,
//                    "Yaay! Anda berada dalam kawasan  ${it.text}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }

//        codeScanner.errorCallback = ErrorCallback {
//            lifecycleScope.launch(Dispatchers.Main) {
//                Toast.makeText(
//                    this,
//                    "Inisiasi kamera gagal: ${it.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }

//        scannerView.setOnClickListener {
//            codeScanner.startPreview()
//        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray,
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == Constants.REQUEST_CODE_PERMISSIONS) {
//            if (requestCode == 123) {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(
//                        this,
//                        "Penggunaan kamera disetujui",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                startScanning()
//            } else {
//                Toast.makeText(this, "Izinkan penggunaan kamera untuk scan", Toast.LENGTH_SHORT)
//                    .show()
//                findNavController().navigateUp()
//            }
//        }
//    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner?.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized) {
            codeScanner?.releaseResources()
        }
        super.onPause()
    }
}