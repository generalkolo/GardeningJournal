package com.miu.cs473.gardenjournal.screens.graden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.miu.cs473.gardenjournal.R
import com.miu.cs473.gardenjournal.adapters.PlantAdapter
import com.miu.cs473.gardenjournal.listeners.PlantClickListener
import com.miu.cs473.gardenjournal.room.entity.Plant
import com.miu.cs473.gardenjournal.screens.viewModels.GardenLogViewModelFactory

class GardenLogFragment : Fragment(R.layout.fragment_graden_log), PlantClickListener {

    private lateinit var viewModel: GardenLogViewModel
    private lateinit var viewModelFactory: GardenLogViewModelFactory
    private lateinit var plantAdapter: PlantAdapter
    private val samplePlants = mutableListOf<Plant>()

    private fun initializeDatabase() {
        samplePlants.add(
            Plant(
                name = "Rose", type = "Flower", wateringFrequency = "2", plantingDate
                = "2023-01-01"
            )
        )
        samplePlants.add(
            Plant(
                name = "Tomato", type = "Vegetable", wateringFrequency = "3",
                plantingDate = "2023-02-15"
            )
        )
        samplePlants.add(
            Plant(
                name = "Basil", type = "Herb", wateringFrequency = "1", plantingDate =
                "2023-03-10"
            )
        )
        for (plant in samplePlants) {
            viewModel.insertPlant(plant)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        plantAdapter = PlantAdapter(this)
        recyclerView.adapter = plantAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        initVariables()

        viewModel.allPlants.observe(viewLifecycleOwner) { plants ->
            plantAdapter.submitList(plants)
        }

        val fab: FloatingActionButton = view.findViewById(R.id.fabAddPlant)
        fab.setOnClickListener {
            showAddPlantDialog()
        }
    }

    private fun initVariables() {
        viewModelFactory = GardenLogViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[GardenLogViewModel::class.java]
    }

    private fun showAddPlantDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_add_plant, null)

        val nameEditText: EditText = view.findViewById(R.id.editTextName)
        val typeSpinner: Spinner = view.findViewById(R.id.spinnerPlantType)
        val frequencyEditText: EditText = view.findViewById(R.id.editTextFrequency)
        val datePicker: DatePicker = view.findViewById(R.id.datePicker)
        val btnShowDatePicker: Button = view.findViewById(R.id.btnShowDatePicker)

        // Set the minimum date to the current date
        datePicker.minDate = System.currentTimeMillis()

        // Retrieve plant types from resources
        val plantTypeArray = resources.getStringArray(R.array.plant_types)

        // Set up the spinner
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, plantTypeArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = adapter

        // Set up the click listener for the button to show the DatePicker
        btnShowDatePicker.setOnClickListener {
            // Toggle the visibility of the DatePicker
            if (datePicker.visibility == View.VISIBLE) {
                datePicker.visibility = View.GONE
            } else {
                datePicker.visibility = View.VISIBLE
            }
        }

        builder.setView(view)
            .setTitle(getString(R.string.add_new_plant))
            .setPositiveButton(getString(R.string.add)) { _, _ ->
                // Retrieve user input
                val name = nameEditText.text.toString()
                val type =
                    plantTypeArray[typeSpinner.selectedItemPosition] // Get selected item from spinner
                val frequency = frequencyEditText.text.toString().toIntOrNull() ?: 0

                // Get selected date from DatePicker
                val day = datePicker.dayOfMonth
                val month = datePicker.month
                val year = datePicker.year

                // Create a new Plant object with the selected date
                val newPlant = Plant(0, name, type, frequency.toString(), "$year-$month-$day")

                // Call the ViewModel method to insert the new plant
                viewModel.insertPlant(newPlant)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onItemClick(plant: Plant) {
        val action =
            GardenLogFragmentDirections.actionGardenLogFragmentToPlantDetailsFragment(plant.id.toLong())
        findNavController().navigate(action)
    }
}
