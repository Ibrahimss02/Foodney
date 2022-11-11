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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.raion.foodney.BuildConfig
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentDetailMissionBinding
import com.raion.foodney.models.Mission
import com.raion.foodney.ui.mainScreens.MainViewModel
import com.raion.foodney.utils.GeofenceBroadcastReceiver
import com.raion.foodney.utils.buildGeofence
import com.raion.foodney.utils.createChannel

class DetailMissionFragment : Fragment(), OnMapReadyCallback{

    private lateinit var binding: FragmentDetailMissionBinding
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var geofencingClient: GeofencingClient
    private lateinit var geofence: Geofence
    private lateinit var map: GoogleMap
    private lateinit var currentMission: Mission
    private lateinit var bottomSheet: BottomSheetBehavior<ConstraintLayout>

    private val deviceQLater = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    // A PendingIntent for the Broadcast Receiver that handles geofence transitions.
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireActivity(), GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        GeofenceBroadcastReceiver(navController = findNavController())
        PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMissionBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupBottomSheet()

        currentMission = viewModel.getCurrentMission(requireArguments().getString("id")!!)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility = View.GONE

        val isFromNotification = requireArguments().getBoolean("isEnteringRadius", false)
        if (isFromNotification) {
            // TODO: SHOW BOTTOM SHEET WITH CLAIM BUTTON
        } else {
            // TODO: SHOW TOAST NOT ENTERED LOCATION YET
        }

        binding.btnPergi.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheet.isDraggable = true

            geofence = buildGeofence(currentMission)
            geofencingClient = LocationServices.getGeofencingClient(requireActivity())
            checkPermissionsAndStartGeofencing()

            binding.btnPergi.visibility = View.GONE
        }

        setupMap()
        createChannel(requireContext())


        return binding.root
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupBottomSheet() {
        bottomSheet = BottomSheetBehavior.from(binding.bottomSheetMission).apply {
            isFitToContents = true
            peekHeight = 400
            isDraggable = false
            state = BottomSheetBehavior.STATE_EXPANDED

            addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            binding.icLogo.visibility = View.GONE
                            binding.backButton.visibility = View.GONE
                            binding.tvFotoHeading.visibility = View.INVISIBLE
                            binding.rvFoto.visibility = View.INVISIBLE
                            binding.tvUlasanHeading.visibility = View.INVISIBLE
                            binding.rvUlasan.visibility = View.INVISIBLE
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            binding.icLogo.visibility = View.VISIBLE
                            binding.backButton.visibility = View.VISIBLE
                            binding.tvFotoHeading.visibility = View.VISIBLE
                            binding.rvFoto.visibility = View.VISIBLE
                            binding.tvUlasanHeading.visibility = View.VISIBLE
                            binding.rvUlasan.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // Nothing
                }
            })
        }
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
        if (viewModel.geofenceIsActive()) return
        if (foregroundAndBackgroundLocationPermissionApproved()) {
            checkDeviceLocationSettingsAndStartGeofence()
        } else {
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
        if (this::geofencingClient.isInitialized) {
            removeGeofences()
        }
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

    private fun checkForegroundLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PermissionChecker.PERMISSION_GRANTED
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
        if (foregroundAndBackgroundLocationPermissionApproved()) return
        var permissionArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        requestPermissionLauncher.launch(permissionArray)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val overlaySize = 15f
        val zoomLevel = 18f

        viewModel.currentMission.value?.let {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(it.latLng, zoomLevel))
            map.addMarker(MarkerOptions().position(it.latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.iv_location)))
            val groundOverlay = GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.iv_red_radius))
                .position(it.latLng, overlaySize)
            map.addGroundOverlay(groundOverlay)
        }

        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (checkForegroundLocationPermission()) {
            map.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        }
    }

    companion object {
        internal const val ACTION_GEOFENCE_EVENT =
            "MapFragment.hunter.action.ACTION_GEOFENCE_EVENT"
    }
}
private const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
private const val TAG = "DetailMissionFragment"