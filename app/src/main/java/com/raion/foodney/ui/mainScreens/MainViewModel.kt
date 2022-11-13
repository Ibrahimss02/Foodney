package com.raion.foodney.ui.mainScreens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.raion.foodney.models.Mission
import com.raion.foodney.models.MissionDummy
import com.raion.foodney.models.Review

class MainViewModel(private val state: SavedStateHandle): ViewModel() {

    val missionList = MissionDummy.missionData
    val warungList = MissionDummy.missionData.apply { shuffle() }
    val completedMission = MissionDummy.completedMission
    val currentMission =  MutableLiveData<Mission>()

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
}
private const val GEOFENCE_STATUS_KEY = "geofenceActive"