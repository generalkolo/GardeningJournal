package com.miu.cs473.gardenjournal.screens.graden

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.miu.cs473.gardenjournal.room.PlantDatabase
import com.miu.cs473.gardenjournal.room.PlantRepository
import com.miu.cs473.gardenjournal.room.entity.Plant
import kotlinx.coroutines.launch

class GardenLogViewModel(application: Application) : AndroidViewModel(application) {
    private val plantRepository: PlantRepository

    val allPlants: LiveData<List<Plant>>

    init {
        val plantDao = PlantDatabase.getDatabase(application)
        plantRepository = PlantRepository(application)
        allPlants = plantRepository.allPlants
    }

    fun insertPlant(plant: Plant) {
        viewModelScope.launch {
            plantRepository.insert(plant)
        }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch {
            plantRepository.update(plant)
        }
    }
}
