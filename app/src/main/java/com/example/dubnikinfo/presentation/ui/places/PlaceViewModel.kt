package com.example.dubnikinfo.presentation.ui.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dubnikinfo.data.local.place.Place
import com.example.dubnikinfo.domain.repository.PlacesRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PlaceViewModel(
    private val placesRepository: PlacesRepository,
) : ViewModel() {
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: MutableStateFlow<List<Place>> = _places

    init {
        viewModelScope.launch {
            _places.value = placesRepository.getPlaces()
        }
    }
}