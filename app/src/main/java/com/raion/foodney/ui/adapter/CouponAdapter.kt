package com.raion.foodney.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raion.foodney.databinding.ItemLayoutCouponBinding
import com.raion.foodney.models.Coupon

class CouponAdapter(private val couponOnClickListener: CouponOnClickListener): ListAdapter<Coupon, CouponAdapter.CouponViewHolder>(CouponDiffUtilCallback()) {

    inner class CouponViewHolder(private val binding: ItemLayoutCouponBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Coupon) {
            binding.coupon = item
            binding.onClickListener = couponOnClickListener
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        return CouponViewHolder(ItemLayoutCouponBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CouponOnClickListener(val clickListener: (coupon: Coupon) -> Unit) {
    fun onClick(coupon: Coupon) = clickListener(coupon)
}

class CouponDiffUtilCallback: DiffUtil.ItemCallback<Coupon>() {
    override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
        return oldItem.couponDesc == newItem.couponDesc
    }
}