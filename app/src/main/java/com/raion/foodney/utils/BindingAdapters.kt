package com.raion.foodney.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raion.foodney.models.Mission
import com.raion.foodney.models.Review
import com.raion.foodney.ui.adapter.WarungMissionAdapter
import com.raion.foodney.ui.adapter.WarungReviewAdapter

@BindingAdapter("warungMissionList")
fun RecyclerView.bindMissionList(list: List<Mission>) {
    val adapter = this.adapter as WarungMissionAdapter
    adapter.submitList(list)
}

@BindingAdapter("warungReviewList")
fun RecyclerView.bindWarungReviewList(list: List<Review>) {
    val adapter = this.adapter as WarungReviewAdapter
    adapter.submitList(list)
}