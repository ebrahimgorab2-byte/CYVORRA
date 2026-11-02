package com.cyvorra.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyvorra.model.DesktopAppDefinition
import com.cyvorra.model.WindowType

@Composable
fun Taskbar(
    apps: List<DesktopAppDefinition>,
    activeWindowId: String?,
    onOpenLauncher: () -> Unit,
    onSelectApp: (DesktopAppDefinition) -> Unit,
    onOpenWindow: (WindowType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.92f))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = onOpenLauncher) {
            Text("Start")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            apps.forEach { app ->
                Button(
                    onClick = {
                        onSelectApp(app)
                        onOpenWindow(app.type)
                    }
                ) {
                    Text(app.title)
                }
            }
        }

        Text(activeWindowId ?: "Desktop")
    }
}
