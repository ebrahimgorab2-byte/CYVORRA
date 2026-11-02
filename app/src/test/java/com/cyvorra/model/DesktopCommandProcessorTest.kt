package com.cyvorra.model

import org.junit.Assert.assertTrue
import org.junit.Test

class DesktopCommandProcessorTest {
    @Test
    fun execute_help_returnsUsageText() {
        val result = DesktopCommandProcessor.execute("help")
        assertTrue(result.contains("Available commands"))
    }

    @Test
    fun execute_echo_returnsText() {
        val result = DesktopCommandProcessor.execute("echo hello")
        assertTrue(result == "hello")
    }
}
