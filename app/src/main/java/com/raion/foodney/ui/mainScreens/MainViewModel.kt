package com.raion.foodney.ui.mainScreens

import androidx.lifecycle.ViewModel
import com.raion.foodney.models.MissionDummy

class MainViewModel: ViewModel() {

    val missionList = MissionDummy.missionData
}