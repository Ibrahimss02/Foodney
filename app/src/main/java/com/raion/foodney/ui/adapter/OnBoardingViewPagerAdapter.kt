package com.raion.foodney.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.raion.foodney.databinding.OnboardingViewpagerItemBinding

class OnBoardingViewPagerAdapter(private val items: ArrayList<Pair<Int, String>>): RecyclerView.Adapter<OnBoardingViewPagerAdapter.ViewPagerViewHolder>() {

    class ViewPagerViewHolder(private val binding: OnboardingViewpagerItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Int, text: String) {
            binding.ivOnBoarding.setImageResource(image)
            binding.tvOnBoarding.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(OnboardingViewpagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(items[position].first, items[position].second)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}