package com.cyvorra.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cyvorra.model.DesktopAppDefinition
import com.cyvorra.model.DesktopCommandProcessor
import com.cyvorra.model.DesktopFileItem
import com.cyvorra.model.DesktopWindowState
import com.cyvorra.model.WindowType

@Composable
fun CyvorraDesktopRoot() {
    val appList = remember {
        listOf(
            DesktopAppDefinition("files", "Files", "Open file manager", "📁", WindowType.FILE_MANAGER),
            DesktopAppDefinition("settings", "Settings", "System settings", "⚙️", WindowType.SETTINGS),
            DesktopAppDefinition("terminal", "Terminal", "System terminal", "💻", WindowType.TERMINAL),
            DesktopAppDefinition("monitor", "System Monitor", "Resource monitor", "📊", WindowType.SYSTEM_MONITOR)
        )
    }

    val initialWindows = remember {
        listOf(
            DesktopWindowState("files", "Files", WindowType.FILE_MANAGER, 70f, 100f, 420f, 300f),
            DesktopWindowState("settings", "Settings", WindowType.SETTINGS, 260f, 200f, 380f, 260f),
            DesktopWindowState("terminal", "Terminal", WindowType.TERMINAL, 180f, 220f, 430f, 290f)
        )
    }

    var windows by remember { mutableStateOf(initialWindows) }
    var activeWindowId by remember { mutableStateOf("files") }
    var launcherOpen by remember { mutableStateOf(false) }
    var terminalInput by remember { mutableStateOf("") }
    var terminalHistory by remember { mutableStateOf(listOf("CYVORRA shell online", "Type help to view commands")) }

    fun syncWindows(newList: List<DesktopWindowState>) {
        windows = newList
    }

    fun openWindow(type: WindowType) {
        val existing = windows.firstOrNull { it.type == type }
        if (existing != null) {
            activeWindowId = existing.id
            return
        }

        val newId = "${type.name.lowercase()}-${windows.size + 1}"
        val newWindow = DesktopWindowState(
            id = newId,
            title = when (type) {
                WindowType.FILE_MANAGER -> "Files"
                WindowType.SETTINGS -> "Settings"
                WindowType.TERMINAL -> "Terminal"
                WindowType.APP_LAUNCHER -> "Launcher"
                WindowType.SYSTEM_MONITOR -> "System Monitor"
            },
            type = type,
            x = 200f,
            y = 180f,
            width = 420f,
            height = 300f,
            isOpen = true
        )

        syncWindows(windows + newWindow)
        activeWindowId = newId
    }

    fun moveWindow(id: String, dx: Float, dy: Float) {
        val updated = windows.map { window ->
            if (window.id != id) return@map window
            window.x += dx
            window.y += dy
            window.clampToBounds(1200f, 900f)
            window
        }
        syncWindows(updated)
    }

    fun resizeWindow(id: String, dx: Float, dy: Float) {
        val updated = windows.map { window ->
            if (window.id != id) return@map window
            window.resizeBy(dx, dy, 1200f, 900f)
            window
        }
        syncWindows(updated)
    }

    fun executeTerminalCommand() {
        val output = DesktopCommandProcessor.execute(terminalInput)
        if (output.isNotBlank()) {
            terminalHistory = terminalHistory + output
        }
        if (terminalInput.equals("clear", ignoreCase = true)) {
            terminalHistory = listOf("CYVORRA shell online")
        }
        terminalInput = ""
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF07111D),
                        Color(0xFF102438),
                        Color(0xFF215E7A)
                    )
                )
            )
            .padding(bottom = 12.dp)
    ) {
        windows.forEach { window ->
            if (window.isOpen) {
                DesktopWindowFrame(
                    state = window,
                    active = window.id == activeWindowId,
                    onDrag = { dx, dy -> moveWindow(window.id, dx, dy) },
                    onResize = { dx, dy -> resizeWindow(window.id, dx, dy) },
                    onFocus = { activeWindowId = window.id },
                    onClose = {
                        val updated = windows.map { if (it.id == window.id) it.copy(isOpen = false) else it }
                        syncWindows(updated)
                    }
                ) {
                    when (window.type) {
                        WindowType.FILE_MANAGER -> FileManagerPanel(
                            listOf(
                                DesktopFileItem("Documents", "Folder", "3.2 GB", "Today"),
                                DesktopFileItem("Downloads", "Folder", "1.1 GB", "Today"),
                                DesktopFileItem("notes.txt", "Text", "32 KB", "Yesterday")
                            )
                        )
                        WindowType.SETTINGS -> SettingsPanel()
                        WindowType.TERMINAL -> TerminalPanel(
                            history = terminalHistory,
                            input = terminalInput,
                            onInputChange = { terminalInput = it },
                            onExecute = { executeTerminalCommand() }
                        )
                        WindowType.SYSTEM_MONITOR -> SystemMonitorPanel()
                        WindowType.APP_LAUNCHER -> Box(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }

        if (launcherOpen) {
            AppLauncherSheet(
                apps = appList,
                onSelect = { app ->
                    launcherOpen = false
                    openWindow(app.type)
                }
            )
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            Taskbar(
                apps = appList,
                activeWindowId = activeWindowId,
                onOpenLauncher = { launcherOpen = !launcherOpen },
                onSelectApp = { app -> openWindow(app.type) },
                onOpenWindow = { type -> openWindow(type) }
            )
        }
    }
}
