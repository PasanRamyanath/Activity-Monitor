package com.example.activitymonitor.data.models

data class EMGData(
    val data: List<Int> = emptyList(),
    val timestamp: Long = 0
)
