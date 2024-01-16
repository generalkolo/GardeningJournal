package com.miu.cs473.gardenjournal.screens.plant

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.miu.cs473.gardenjournal.R
import com.miu.cs473.gardenjournal.databinding.FragmentPlantDetailsBinding
import com.miu.cs473.gardenjournal.room.PlantRepository
import com.miu.cs473.gardenjournal.screens.viewModels.PlantDetailsViewModelFactory

class PlantDetailsFragment : Fragment(R.layout.fragment_plant_details) {

    private lateinit var viewModel: PlantDetailsViewModel
    private lateinit var viewModelFactory: PlantDetailsViewModelFactory
    private lateinit var binding: FragmentPlantDetailsBinding
    private lateinit var plantRepository: PlantRepository
    private var plantId: Long = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPlantDetailsBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner

        arguments?.let {
            plantId = PlantDetailsFragmentArgs.fromBundle(it).plantId
        }

        initVariables()

        viewModel.getPlantDetails(plantId)
        viewModel.plantDetails.observe(viewLifecycleOwner) { plant ->
            plant?.let {
                binding.plant = it
            }
        }
    }

    fun initVariables() {
        plantRepository = PlantRepository(requireActivity().application)
        viewModelFactory = PlantDetailsViewModelFactory(plantRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[PlantDetailsViewModel::class.java]
    }
}
