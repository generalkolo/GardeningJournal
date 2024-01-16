package com.miu.cs473.gardenjournal.screens.plant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miu.cs473.gardenjournal.room.PlantRepository
import com.miu.cs473.gardenjournal.room.entity.Plant
import kotlinx.coroutines.launch

class PlantDetailsViewModel(private val plantRepository: PlantRepository) : ViewModel() {

    private val _plantDetails = MutableLiveData<Plant>()
    val plantDetails: LiveData<Plant> get() = _plantDetails

    fun getPlantDetails(plantId: Long) {
        viewModelScope.launch {
            val plant = plantRepository.getPlantById(plantId.toInt())
            _plantDetails.postValue(plant)
        }
    }
}
