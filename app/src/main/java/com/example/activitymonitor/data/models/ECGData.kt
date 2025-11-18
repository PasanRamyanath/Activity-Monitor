package com.example.activitymonitor.data.models

data class ECGData(
    val data: List<Int> = emptyList(),
    val timestamp: Long = 0
)