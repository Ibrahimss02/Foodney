package com.raion.foodney.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raion.foodney.databinding.ItemLayoutLeaderboardBinding
import com.raion.foodney.models.UserLeaderboard

class LeaderboardAdapter: ListAdapter<UserLeaderboard, LeaderboardAdapter.LeaderboardViewHolder>(UserLeaderboardDiffUtilCallback()) {

    class LeaderboardViewHolder(private val binding: ItemLayoutLeaderboardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int, item: UserLeaderboard) {
            binding.index = index
            binding.userLeaderboard = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        return LeaderboardViewHolder(ItemLayoutLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        if (position > 3) {
            holder.bind(position, getItem(position))
        }
    }
}

class UserLeaderboardDiffUtilCallback: DiffUtil.ItemCallback<UserLeaderboard>() {
    override fun areItemsTheSame(oldItem: UserLeaderboard, newItem: UserLeaderboard): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserLeaderboard, newItem: UserLeaderboard): Boolean {
        return oldItem == newItem
    }
}