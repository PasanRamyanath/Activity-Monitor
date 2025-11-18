// MainViewModel.kt
package com.example.activitymonitor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activitymonitor.data.models.ECGData
import com.example.activitymonitor.data.models.EMGData
import com.example.activitymonitor.data.models.HeartRateData
import com.example.activitymonitor.data.repository.SensorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = SensorRepository()

    private val _ecgData = MutableStateFlow(ECGData())
    val ecgData: StateFlow<ECGData> = _ecgData.asStateFlow()

    private val _emgData = MutableStateFlow(EMGData())
    val emgData: StateFlow<EMGData> = _emgData.asStateFlow()

    private val _heartRateData = MutableStateFlow(HeartRateData())
    val heartRateData: StateFlow<HeartRateData> = _heartRateData.asStateFlow()

    init {
        collectECGData()
        collectEMGData()
        collectHeartRateData()
    }

    private fun collectECGData() {
        viewModelScope.launch {
            repository.getECGData().collectLatest { data ->
                _ecgData.value = data
            }
        }
    }

    private fun collectEMGData() {
        viewModelScope.launch {
            repository.getEMGData().collectLatest { data ->
                _emgData.value = data
            }
        }
    }

    private fun collectHeartRateData() {
        viewModelScope.launch {
            repository.getHeartRateData().collectLatest { data ->
                _heartRateData.value = data
            }
        }
    }
}