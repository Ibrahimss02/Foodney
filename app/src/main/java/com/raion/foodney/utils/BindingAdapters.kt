package com.raion.foodney.utils

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raion.foodney.R
import com.raion.foodney.models.*
import com.raion.foodney.ui.adapter.*

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
        adapter.submitList(list.drop(3))
    } else {
        val adapter = this.adapter as LeaderboardAdapter
        adapter.submitList(LeaderboardDummy.leaderboardData.drop(3))
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

@BindingAdapter("couponList")
fun RecyclerView.bindCouponList(list: List<Coupon>?) {
    if (list != null) {
        val adapter = this.adapter as CouponAdapter
        adapter.submitList(list)
    } else {
        val adapter = this.adapter as CouponAdapter
        adapter.submitList(CouponDummy.couponData)
    }
}

@BindingAdapter("imageSrc")
fun ImageView.bindImage(image: Int?) {
    Glide.with(this.context).apply {
        if (image != null && image != 0) {
            load(AppCompatResources.getDrawable(this@bindImage.context, image))
        } else {
            load(AppCompatResources.getDrawable(this@bindImage.context, R.drawable.iv_image_error_placeholder))
        }
            .centerCrop()
            .into(this@bindImage)
    }
}