package com.miu.cs473.gardenjournal.screens.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miu.cs473.gardenjournal.room.PlantRepository
import com.miu.cs473.gardenjournal.screens.plant.PlantDetailsViewModel

class PlantDetailsViewModelFactory(private val plantRepository: PlantRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlantDetailsViewModel(plantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}