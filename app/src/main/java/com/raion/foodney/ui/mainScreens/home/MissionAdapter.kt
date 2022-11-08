package com.raion.foodney.ui.mainScreens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raion.foodney.databinding.ItemLayoutMissionBinding
import com.raion.foodney.models.Mission

class MissionAdapter: ListAdapter<Mission, MissionAdapter.MissionViewHolder>(MissionDiffUtilCallback()) {

    class MissionViewHolder(private val binding: ItemLayoutMissionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Mission) {
            binding.mission = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionViewHolder {
        return MissionViewHolder(ItemLayoutMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MissionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MissionDiffUtilCallback: DiffUtil.ItemCallback<Mission>() {
    override fun areItemsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        return oldItem.name == newItem.name
    }
}