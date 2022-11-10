package com.raion.foodney.ui.mainScreens.missionDetail

import android.Manifest
import android.annotation.TargetApi
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.raion.foodney.BuildConfig
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentDetailMissionBinding
import com.raion.foodney.models.Mission
import com.raion.foodney.ui.mainScreens.MainViewModel
import com.raion.foodney.utils.GeofenceBroadcastReceiver
import com.raion.foodney.utils.buildGeofence
import com.raion.foodney.utils.createChannel

class DetailMissionFragment : Fragment() {

    private lateinit var binding: FragmentDetailMissionBinding
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var geofencingClient: GeofencingClient
    private lateinit var geofence: Geofence
    private lateinit var map: GoogleMap
    private lateinit var currentMission: Mission

    private val deviceQLater = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    // A PendingIntent for the Broadcast Receiver that handles geofence transitions.
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireActivity(), GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        GeofenceBroadcastReceiver(navController = findNavController())
        PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onStart() {
        super.onStart()
        checkPermissionsAndStartGeofencing()
        Log.d(TAG, "Starting Geofencing Check")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMissionBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        currentMission = viewModel.getCurrentMission(requireArguments().getString("id")!!)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility = View.GONE

        val isFromNotification = requireArguments().getBoolean("isEnteringRadius", false)
        if (isFromNotification) {
            // TODO: SHOW BOTTOM SHEET WITH CLAIM BUTTON
        } else {
            // TODO: SHOW TOAST NOT ENTERED LOCATION YET
        }

        geofence = buildGeofence(currentMission)
        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
        createChannel(requireContext())


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility = View.VISIBLE
    }

    private fun checkPermissionsAndStartGeofencing() {
        Log.d(TAG, "Checking Permission")
        if (viewModel.geofenceIsActive()) return
        Log.d(TAG, "Geofencing not active")
        if (foregroundAndBackgroundLocationPermissionApproved()) {
            Log.d(TAG, "Approved")
            checkDeviceLocationSettingsAndStartGeofence()
        } else {
            Log.d(TAG, "Requesting")
            requestForegroundAndBackgroundLocationPermissions()
        }
    }

    private fun getGeofencingRequest(): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofence(geofence)
        }.build()
    }

    private fun checkDeviceLocationSettingsAndStartGeofence(resolve: Boolean = true) {
        Log.d(TAG, "Checking Location")
        val locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(requireActivity())
        val locationSettingResponseTask = settingsClient.checkLocationSettings(builder.build())
        locationSettingResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                try {
                    exception.startResolutionForResult(requireActivity(), REQUEST_TURN_DEVICE_LOCATION_ON)
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "Error getting location settings resolution: " + sendEx.message)
                }
            } else {
                Snackbar.make(
                    binding.detailMissionFragment,
                    R.string.location_required_error,
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    checkDeviceLocationSettingsAndStartGeofence()
                }.show()
            }
        }

        locationSettingResponseTask.addOnCompleteListener {
            addGeofences()
        }
    }

    private fun addGeofences() {
        Log.d(TAG, "Adding Geofences")
        if (viewModel.geofenceIsActive()) return
        geofencingClient.removeGeofences(geofencePendingIntent).run {
            addOnCompleteListener {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestForegroundAndBackgroundLocationPermissions()
                    return@addOnCompleteListener
                }
                geofencingClient.addGeofences(getGeofencingRequest(), geofencePendingIntent).run {
                    addOnSuccessListener {
                        Log.d(TAG, "Adding Success")
                        Toast.makeText(requireContext(), getString(R.string.geofences_added, currentMission.name), Toast.LENGTH_SHORT).show()
                        viewModel.geofenceActivated()
                    }
                    addOnFailureListener {
                        Log.d(TAG, "Adding Failed")
                        Toast.makeText(requireContext(), R.string.geofences_not_added, Toast.LENGTH_SHORT).show()
                        if (it.message != null) {
                            Log.w(TAG, it.message!!)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeGeofences()
    }

    private fun removeGeofences() {
        if (!foregroundAndBackgroundLocationPermissionApproved()) {
            return
        }

        geofencingClient.removeGeofences(geofencePendingIntent).run {
            addOnSuccessListener {
                Log.d(TAG, "Geofences Removed")
            }

            addOnFailureListener {
                Log.d(TAG, "Geofences not removed")
            }
        }
    }

    @TargetApi(29)
    private fun foregroundAndBackgroundLocationPermissionApproved(): Boolean {
        val foregroundPermission = (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
        val backgroundPermission = if (deviceQLater) {
            ContextCompat.checkSelfPermission(
                requireContext(),
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
            checkDeviceLocationSettingsAndStartGeofence()
        } else {
            // Permission Denied
            Log.d(TAG, "Asking Permission Denied")
            Snackbar.make(
                binding.detailMissionFragment,
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

    @TargetApi(29)
    private fun requestForegroundAndBackgroundLocationPermissions() {
        Log.d(TAG, "Asking Permission Check")
        if (foregroundAndBackgroundLocationPermissionApproved()) return
        Log.d(TAG, "Asking Permission Check Approved")
        var permissionArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        val requestCode = when {
            deviceQLater -> {
                permissionArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION
                REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE
            }
            else -> {
                REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
            }
        }

        Log.d(TAG, "Asking Permission Main")
        requestPermissionLauncher.launch(permissionArray)
    }


    companion object {
        internal const val ACTION_GEOFENCE_EVENT =
            "MapFragment.hunter.action.ACTION_GEOFENCE_EVENT"
    }
}
private const val REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE = 33
private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
private const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
private const val TAG = "DetailMissionFragment"