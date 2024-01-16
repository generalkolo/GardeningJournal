package com.miu.cs473.gardenjournal.listeners

import com.miu.cs473.gardenjournal.room.entity.Plant

interface PlantClickListener {
    fun onItemClick(plant: Plant)
}