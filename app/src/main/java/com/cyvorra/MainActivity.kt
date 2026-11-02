package com.cyvorra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cyvorra.desktop.CyvorraDesktopRoot
import com.cyvorra.ui.theme.CyvorraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CyvorraTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CyvorraDesktopRoot()
                }
            }
        }
    }
}
