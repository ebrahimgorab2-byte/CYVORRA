package com.cyvorra.model

import java.time.LocalDateTime

object DesktopCommandProcessor {
    fun execute(command: String): String {
        val trimmed = command.trim()
        if (trimmed.isEmpty()) return ""

        return when {
            trimmed.equals("help", ignoreCase = true) -> "Available commands: help, ls, echo, date, open settings"
            trimmed.equals("ls", ignoreCase = true) -> "Documents\nDownloads\nPictures\nSystem\nWorkspace"
            trimmed.equals("date", ignoreCase = true) -> LocalDateTime.now().toString()
            trimmed.equals("open settings", ignoreCase = true) -> "Opening settings panel..."
            trimmed.startsWith("echo ", ignoreCase = true) -> trimmed.removePrefix("echo ").trim()
            else -> "Unknown command: $trimmed"
        }
    }
}
