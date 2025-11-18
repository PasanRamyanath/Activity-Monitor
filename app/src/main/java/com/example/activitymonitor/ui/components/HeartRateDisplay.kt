package com.example.activitymonitor.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activitymonitor.data.models.HeartRateData
import kotlinx.coroutines.delay

@Composable
fun HeartRateDisplay(heartRateData: HeartRateData, modifier: Modifier = Modifier) {
    var isBeating by remember { mutableStateOf(false) }

    LaunchedEffect(heartRateData) {
        if (heartRateData.value > 0) {
            // Calculate beat interval in milliseconds from BPM
            val beatInterval = (60000 / heartRateData.value).toLong()
            while (true) {
                isBeating = true
                delay(100) // Flash on for 100ms
                isBeating = false
                delay(beatInterval - 100) // Wait for next beat
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Heart Rate",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Surface(
            shape = CircleShape,
            color = if (isBeating) Color(0xFFFF5722) else Color(0xFFE57373),
            modifier = Modifier.size(120.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "${heartRateData.value}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "BPM",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp)
                )
            }
        }
    }
}