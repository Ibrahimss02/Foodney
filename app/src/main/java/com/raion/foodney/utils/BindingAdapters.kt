package com.raion.foodney.utils

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raion.foodney.models.*
import com.raion.foodney.ui.adapter.LeaderboardAdapter
import com.raion.foodney.ui.adapter.MyCouponAdapter
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

@BindingAdapter("userLeaderboardList")
fun RecyclerView.bindUserLeaderboardList(list: List<UserLeaderboard>?) {
    if (list != null) {
        val adapter = this.adapter as LeaderboardAdapter
        adapter.submitList(list)
    } else {
        val adapter = this.adapter as LeaderboardAdapter
        adapter.submitList(LeaderboardDummy.leaderboardData)
    }
}

@BindingAdapter("myCouponList")
fun RecyclerView.bindMyCouponList(list: List<Coupon>?) {
    if (list != null) {
        val adapter = this.adapter as MyCouponAdapter
        adapter.submitList(list)
    } else {
        val adapter = this.adapter as MyCouponAdapter
        adapter.submitList(CouponDummy.couponData)
    }
}

@BindingAdapter("imageSrc")
fun ImageView.bindImage(image: Int) {
    Glide.with(this.context)
        .load(AppCompatResources.getDrawable(this.context, image))
        .into(this)
}