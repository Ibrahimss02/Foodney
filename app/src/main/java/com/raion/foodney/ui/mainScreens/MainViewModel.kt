package com.raion.foodney.ui.mainScreens

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.auth.User
import com.raion.foodney.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _userProfile = MutableLiveData<Profile?>()
    val userProfile: LiveData<Profile?> = _userProfile

    val missionList = MissionDummy.missionData
    val warungList = MissionDummy.missionData.apply { shuffle() }
    val couponList = CouponDummy.couponData
    val currentMission = MutableLiveData<Mission>()
    private val firebaseDatabase =
        FirebaseDatabase.getInstance("https://foodney-49058-default-rtdb.firebaseio.com/")
            .getReference("users")

    init {
        getUserData()
    }

    fun getCurrentMission(id: String): Mission {
        return if (id == "none") {
            missionList[0]
        } else {
            val mission = missionList.first { it.id == id }
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

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseDatabase.child(auth.currentUser!!.uid).get().addOnSuccessListener {
                _userProfile.value = it.getValue(Profile::class.java)
            }
        }
    }

    private fun updateUserData(uid: String, profile: Profile) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseDatabase.child(uid).setValue(profile)
        }
    }

    fun claimReward(mission: Mission) {
        val profile = _userProfile.value?.copy()
        profile?.let {
            it.coins += mission.coinReward
            if (it.completedMission == null) {
                it.completedMission = mutableListOf<Mission>().apply { add(mission) }
            } else {
                it.completedMission!!.toMutableList().add(mission)
            }
            updateUserData(auth.currentUser!!.uid, it)
            _userProfile.value = it
        }
        _userProfile.value = profile
        updateUserData(_userProfile.value!!.uid, _userProfile.value!!)
    }

    fun signOut() {
        _userProfile.value = null
        auth.signOut()
    }

    fun buyCoupon(coupon: Coupon) {
        val profile = _userProfile.value?.copy()
        profile?.let {
            it.coins -= coupon.cost
            if (it.ownedCoupon == null) {
                it.ownedCoupon = mutableListOf<Coupon>().apply { add(coupon) }
            } else {
                it.ownedCoupon!!.toMutableList().add(coupon)
            }
            updateUserData(auth.currentUser!!.uid, it)
            _userProfile.value = it
        }
        _userProfile.value = profile
        updateUserData(_userProfile.value!!.uid, _userProfile.value!!)
    }

    /*
    Data For Leaderboard
     */
    val userLeaderboardData = LeaderboardDummy.leaderboardData
    val user1 = LeaderboardDummy.leaderboardData[0]
    val user2 = LeaderboardDummy.leaderboardData[1]
    val user3 = LeaderboardDummy.leaderboardData[2]
}

private const val GEOFENCE_STATUS_KEY = "geofenceActive"