package com.raion.foodney.ui.mainScreens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.raion.foodney.models.Mission
import com.raion.foodney.models.MissionDummy
import com.raion.foodney.models.Review

class MainViewModel(private val state: SavedStateHandle): ViewModel() {

    val missionList = MissionDummy.missionData
    val warungList = MissionDummy.missionData.apply { shuffle() }
    val completedMission = MissionDummy.completedMission
    val currentMission =  MutableLiveData<Mission>()
    private val firebaseDatabase =
        FirebaseDatabase.getInstance("https://foodney-49058-default-rtdb.firebaseio.com/").getReference("user")

    fun getCurrentMission(id: String): Mission {
        return if (id == "none") {
            missionList[0]
        } else {
            val mission = missionList.first{ it.id == id }
            currentMission.value = mission
            mission
        }
    }

    private val _geofenceStatus = state.getLiveData(GEOFENCE_STATUS_KEY, false)
    val geofenceStatus: LiveData<Boolean>
        get() = _geofenceStatus

    fun geofenceIsActive() = _geofenceStatus.value!!
    fun geofenceActivated() {
        _geofenceStatus.value = true
        state[GEOFENCE_STATUS_KEY] = true
        Log.d("MapViewModel", "Geofence activated")
    }

    fun getUserData(uid: String) {
        firebaseDatabase.child(uid).get().addOnSuccessListener {
            // val user = it.value
        }
    }
}
private const val GEOFENCE_STATUS_KEY = "geofenceActive"