package com.raion.foodney.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raion.foodney.models.Mission
import com.raion.foodney.ui.mainScreens.home.MissionAdapter

@BindingAdapter("missionList")
fun RecyclerView.bindMissionList(list: List<Mission>) {
    val adapter = this.adapter as MissionAdapter
    adapter.submitList(list)
}