package com.miu.cs473.gardenjournal.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.miu.cs473.gardenjournal.room.entity.Plant

@Dao
interface PlantDao {

    // LiveData to observe the list of plants
    @Query("SELECT * FROM plants")
    fun getAllPlants(): LiveData<List<Plant>>

    // Coroutine to insert a new plant
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant)

    @Update
    suspend fun update(plant: Plant)

    @Query("DELETE FROM plants")
    suspend fun deleteAll()

    @Query("DELETE FROM plants WHERE id = :plantId")
    suspend fun delete(plantId: Int)

    @Query("SELECT * FROM plants WHERE id = :plantId")
    suspend fun getPlantById(plantId: Int): Plant
}
