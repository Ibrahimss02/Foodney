package com.raion.foodney.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raion.foodney.databinding.ItemLayoutImageBinding

class WarungImageAdapter(private val imageList: List<Int>): RecyclerView.Adapter<WarungImageAdapter.WarungImageViewHolder>() {

    class WarungImageViewHolder(private val binding: ItemLayoutImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Int) {
            binding.image = image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarungImageViewHolder {
        return WarungImageViewHolder(ItemLayoutImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WarungImageViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}