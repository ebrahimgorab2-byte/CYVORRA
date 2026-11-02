package com.cyvorra.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.cyvorra.model.DesktopWindowState
import kotlin.math.roundToInt

@Composable
fun DesktopWindowFrame(
    state: DesktopWindowState,
    active: Boolean,
    onDrag: (Float, Float) -> Unit,
    onResize: (Float, Float) -> Unit,
    onFocus: () -> Unit,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    val widthDp = with(LocalDensity.current) { state.width.toDp() }
    val heightDp = with(LocalDensity.current) { state.height.toDp() }
    val titleColor = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = Modifier
            .offset { IntOffset(state.x.roundToInt(), state.y.roundToInt()) }
            .width(widthDp)
            .height(heightDp)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.97f), RoundedCornerShape(18.dp))
            .border(1.dp, titleColor.copy(alpha = 0.8f), RoundedCornerShape(18.dp))
            .pointerInput(state.id) {
                detectDragGestures(onDragStart = { onFocus() }) { _, dragAmount ->
                    onDrag(dragAmount.x, dragAmount.y)
                }
            }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(titleColor.copy(alpha = 0.16f))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = state.title, style = MaterialTheme.typography.titleMedium)
                Row {
                    IconButton(onClick = { }) { Icon(Icons.Default.Minimize, contentDescription = null) }
                    IconButton(onClick = { }) { Icon(Icons.Default.FullscreenExit, contentDescription = null) }
                    IconButton(onClick = onClose) { Icon(Icons.Default.Close, contentDescription = null) }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                content = { content() }
            )

            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(10.dp)
                    .size(18.dp)
                    .background(Color.White.copy(alpha = 0.18f), RoundedCornerShape(6.dp))
                    .pointerInput(state.id) {
                        detectDragGestures { _, dragAmount ->
                            onResize(dragAmount.x, dragAmount.y)
                        }
                    }
            )
        }
    }
}
