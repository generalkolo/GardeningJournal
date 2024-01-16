package com.miu.cs473.gardenjournal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miu.cs473.gardenjournal.databinding.PlantItemBinding
import com.miu.cs473.gardenjournal.listeners.PlantClickListener
import com.miu.cs473.gardenjournal.room.entity.Plant

class PlantAdapter(private val listener: PlantClickListener) :
    ListAdapter<Plant, PlantAdapter.ViewHolder>(PlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PlantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = getItem(position)
        holder.bind(plant)
    }

    inner class ViewHolder(private val binding: PlantItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plant: Plant) {
            binding.plant = plant
            binding.root.setOnClickListener { listener.onItemClick(plant) }
            binding.executePendingBindings()
        }
    }
}


class PlantDiffCallback : DiffUtil.ItemCallback<Plant>() {
    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
    }
}
