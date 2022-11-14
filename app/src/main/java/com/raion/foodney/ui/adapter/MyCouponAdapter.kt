package com.raion.foodney.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raion.foodney.databinding.ItemLayoutMyCouponBinding
import com.raion.foodney.models.Coupon

class MyCouponAdapter: ListAdapter<Coupon, MyCouponAdapter.MyCouponViewHolder>(CouponDiffUtilCallback()) {

    inner class MyCouponViewHolder(private val binding: ItemLayoutMyCouponBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Coupon) {
            binding.coupon = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCouponViewHolder {
        return MyCouponViewHolder(ItemLayoutMyCouponBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyCouponViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}