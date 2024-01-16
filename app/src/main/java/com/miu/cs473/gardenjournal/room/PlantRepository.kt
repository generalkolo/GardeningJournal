package com.miu.cs473.gardenjournal.room

import android.app.Application
import androidx.lifecycle.LiveData
import com.miu.cs473.gardenjournal.room.entity.Plant

class PlantRepository(application: Application) {
    private val plantDao: PlantDao
    val allPlants: LiveData<List<Plant>>

    init {
        val database = PlantDatabase.getDatabase(application)
        plantDao = database.plantDao()
        allPlants = plantDao.getAllPlants()
    }

    suspend fun insert(plant: Plant) {
        plantDao.insertPlant(plant)
    }

    suspend fun update(plant: Plant) {
        plantDao.update(plant)
    }

    suspend fun delete(plant: Plant) {
        plantDao.delete(plant.id)
    }

    suspend fun getPlantById(plantId: Int): Plant {
        return plantDao.getPlantById(plantId)
    }
}


