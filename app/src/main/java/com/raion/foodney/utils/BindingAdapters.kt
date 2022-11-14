package com.raion.foodney.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raion.foodney.models.Mission
import com.raion.foodney.models.Review
import com.raion.foodney.models.ReviewDummy
import com.raion.foodney.ui.adapter.WarungMissionAdapter
import com.raion.foodney.ui.adapter.WarungReviewAdapter

@BindingAdapter("warungMissionList")
fun RecyclerView.bindMissionList(list: List<Mission>?) {
    list?.let {
        val adapter = this.adapter as WarungMissionAdapter
        adapter.submitList(it)
    }
}

@BindingAdapter("warungReviewList")
fun RecyclerView.bindWarungReviewList(list: List<Review>?) {
    if (list != null) {
        val adapter = this.adapter as WarungReviewAdapter
        adapter.submitList(list)
    } else {
        val adapter = this.adapter as WarungReviewAdapter
        adapter.submitList(ReviewDummy.reviewData)
    }
}