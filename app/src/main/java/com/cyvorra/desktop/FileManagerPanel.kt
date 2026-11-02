package com.cyvorra.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyvorra.model.DesktopFileItem

@Composable
fun FileManagerPanel(files: List<DesktopFileItem>) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f), RoundedCornerShape(12.dp))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Name")
            Text("Type")
            Text("Size")
        }

        files.forEach { file ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(file.name)
                Text(file.type)
                Text(file.size)
            }
        }
    }
}

@Composable
fun SystemMonitorPanel() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        StatRow("CPU", "42%")
        StatRow("Memory", "6.8 / 16 GB")
        StatRow("Battery", "96%")
        StatRow("Network", "Wi‑Fi connected")
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = "$label: $value")
    }
}
