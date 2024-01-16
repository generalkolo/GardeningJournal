package com.miu.cs473.gardenjournal.screens.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miu.cs473.gardenjournal.screens.graden.GardenLogViewModel


class GardenLogViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GardenLogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GardenLogViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}