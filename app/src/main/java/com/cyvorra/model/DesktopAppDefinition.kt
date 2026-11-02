package com.cyvorra.model

data class DesktopAppDefinition(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val type: WindowType
)
