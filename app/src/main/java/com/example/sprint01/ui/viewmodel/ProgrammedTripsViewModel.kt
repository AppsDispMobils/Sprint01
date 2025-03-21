package com.example.sprint01.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sprint01.domain.model.Trip
import com.example.sprint01.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgrammedTripsViewModel @Inject constructor(
    private val tripRepository: TripRepository
) : ViewModel() {

    private val _trips = mutableStateListOf<Trip>()
    val trips = _trips


    private fun loadTrips() {
        trips.clear()
        trips.addAll(tripRepository.getTrips())
        Log.d("Trip", "Showing all trips")
    }

    fun addTrip(trip: Trip) {
        viewModelScope.launch {
            tripRepository.addTrip(trip)
            Log.d("Trip", "Added new trip: ${trip.destination}")
            loadTrips()
        }
    }

    fun deleteTrip(tripId: Int) {
        viewModelScope.launch {
            tripRepository.deleteTrip(tripId)
            Log.d("Trip", "Deleting trip...")
            loadTrips()
        }
    }

    fun updateTrip(trip: Trip) {
        viewModelScope.launch {
            tripRepository.updateTrip(trip)
            Log.d("Trip", "Updated ${trip.destination}")
            loadTrips()
        }
    }


}