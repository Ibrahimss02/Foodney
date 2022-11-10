package com.raion.foodney.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raion.foodney.models.Mission
import com.raion.foodney.ui.adapter.WarungMissionAdapter

@BindingAdapter("warungMissionList")
fun RecyclerView.bindMissionList(list: List<Mission>) {
    val adapter = this.adapter as WarungMissionAdapter
    adapter.submitList(list)
}