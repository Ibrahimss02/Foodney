package com.raion.foodney.ui.mainScreens.camera

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.ImageCapture
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.raion.foodney.R
import com.raion.foodney.databinding.ActivityCameraBinding
import com.raion.foodney.ui.mainScreens.coupons.Constants

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun startScanning() {
        val scannerView: CodeScannerView = binding.viewFinder
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(
                    this,
                    "Yaay! Anda berada dalam kawasan  ${it.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(
                    this,
                    "Inisiasi kamera gagal: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_CODE_PERMISSIONS) {
            if (requestCode == 123) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "Penggunaan kamera disetujui",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                startScanning()
            } else {
                Toast.makeText(this, "Izinkan penggunaan kamera untuk scan", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized) {
            codeScanner.releaseResources()
        }
        super.onPause()
    }
}