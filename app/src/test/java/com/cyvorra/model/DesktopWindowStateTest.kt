package com.cyvorra.model

import org.junit.Assert.assertTrue
import org.junit.Test

class DesktopWindowStateTest {
    @Test
    fun clampToBounds_keepsWindowInsideDesktopBounds() {
        val state = DesktopWindowState(
            id = "test-window",
            title = "Test",
            type = WindowType.SETTINGS,
            x = 2000f,
            y = 1500f,
            width = 500f,
            height = 300f
        )

        state.clampToBounds(1200f, 900f)

        assertTrue(state.x <= 1200f)
        assertTrue(state.y <= 900f)
    }

    @Test
    fun resizeBy_keepsMinimumSize() {
        val state = DesktopWindowState(
            id = "file-manager",
            title = "Files",
            type = WindowType.FILE_MANAGER,
            x = 50f,
            y = 50f,
            width = 400f,
            height = 250f
        )

        state.resizeBy(-1000f, -1000f, 1200f, 900f)

        assertTrue(state.width >= state.minWidth)
        assertTrue(state.height >= state.minHeight)
    }
}
