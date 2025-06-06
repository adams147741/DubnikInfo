package com.example.dubnikinfo.presentation.ui.garbageCollection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dubnikinfo.data.local.trash.TrashEntity
import com.example.dubnikinfo.data.local.trash.TrashType
import com.example.dubnikinfo.domain.repository.TrashRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class GarbageViewModel(
    private val trashRepository: TrashRepository
) : ViewModel() {
    private val _pickups = MutableStateFlow(mapOf<LocalDate, List<TrashType>>())
    val pickups: StateFlow<Map<LocalDate, List<TrashType>>> = _pickups

    init {
        viewModelScope.launch {
            _pickups.value = trashRepository.getTrashMap()
        }
    }
}