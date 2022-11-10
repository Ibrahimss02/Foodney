package com.raion.foodney.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raion.foodney.databinding.ItemLayoutMissionBinding
import com.raion.foodney.models.Mission

class WarungMissionAdapter(private val onClickWarungMissionListener: OnClickWarungMissionListener) : ListAdapter<Mission, WarungMissionAdapter.MissionViewHolder>(MissionDiffUtilCallback()) {

    inner class MissionViewHolder(private val binding: ItemLayoutMissionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Mission) {
            binding.mission = item
            binding.clickListener = onClickWarungMissionListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionViewHolder {
        return MissionViewHolder(ItemLayoutMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MissionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class OnClickWarungMissionListener(val clickListener: (missionId: String) -> Unit) {
    fun onClick(missionId: String) = clickListener(missionId)
}

class MissionDiffUtilCallback: DiffUtil.ItemCallback<Mission>() {
    override fun areItemsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        return oldItem.name == newItem.name
    }
}