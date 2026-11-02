package com.cyvorra.model

import kotlin.math.max
import kotlin.math.min

data class DesktopWindowState(
    val id: String,
    val title: String,
    val type: WindowType,
    var x: Float,
    var y: Float,
    var width: Float,
    var height: Float,
    val minWidth: Float = 280f,
    val minHeight: Float = 180f,
    var isOpen: Boolean = true
) {
    fun clampToBounds(boundsWidth: Float, boundsHeight: Float) {
        val maxX = max(0f, boundsWidth - width)
        val maxY = max(0f, boundsHeight - height)
        x = min(max(0f, x), maxX)
        y = min(max(0f, y), maxY)
    }

    fun resizeBy(deltaWidth: Float, deltaHeight: Float, boundsWidth: Float, boundsHeight: Float) {
        width = (width + deltaWidth).coerceIn(minWidth, boundsWidth)
        height = (height + deltaHeight).coerceIn(minHeight, boundsHeight)
        clampToBounds(boundsWidth, boundsHeight)
    }
}
