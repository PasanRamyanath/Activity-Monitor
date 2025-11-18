package com.example.activitymonitor.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.activitymonitor.data.models.EMGData

@Composable
fun EMGChart(emgData: EMGData, modifier: Modifier = Modifier) {
    Text(
        text = "EMG Data",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFEEEEEE))
    ) {
        val data = emgData.data
        if (data.isEmpty()) return@Canvas

        val path = Path()
        val maxValue = data.maxOrNull()?.toFloat() ?: 4095f
        val minValue = data.minOrNull()?.toFloat() ?: 0f
        val range = maxValue - minValue

        val width = size.width
        val height = size.height
        val xStep = width / (data.size - 1)

        // Start from the first point
        val x0 = 0f
        val y0 = height - ((data[0] - minValue) / range * height)
        path.moveTo(x0, y0)

        // Draw line to each subsequent point
        for (i in 1 until data.size) {
            val x = i * xStep
            val y = height - ((data[i] - minValue) / range * height)
            path.lineTo(x, y)
        }

        // Draw the path
        drawPath(
            path = path,
            color = Color(0xFF4CAF50),
            style = Stroke(width = 2f)
        )

        // Draw grid lines
        val gridColor = Color.Gray.copy(alpha = 0.3f)
        val gridStep = height / 4

        // Horizontal grid lines
        for (i in 0..4) {
            val y = i * gridStep
            drawLine(
                color = gridColor,
                start = Offset(0f, y),
                end = Offset(width, y)
            )
        }

        // Vertical grid lines
        val vGridCount = 10
        val vGridStep = width / vGridCount
        for (i in 0..vGridCount) {
            val x = i * vGridStep
            drawLine(
                color = gridColor,
                start = Offset(x, 0f),
                end = Offset(x, height)
            )
        }
    }
}