package com.example.dubnikinfo.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dubnikinfo.data.local.trash.TrashType
import com.example.dubnikinfo.domain.repository.TrashRepository
import com.example.dubnikinfo.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeViewModel(
    private val trashRepository: TrashRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _firstPickups = MutableStateFlow(mapOf<LocalDate, List<TrashType>>())
    val firstPickups: StateFlow<Map<LocalDate, List<TrashType>>> = _firstPickups

    private val _temperature = MutableStateFlow<Double?>(null)
    val temperature: StateFlow<Double?> = _temperature

    init {
        viewModelScope.launch {
            _firstPickups.value = trashRepository.getFirstPickups()
            _temperature.value = weatherRepository.getLocalTemperature(47.9571, 18.4111)
        }
    }
}