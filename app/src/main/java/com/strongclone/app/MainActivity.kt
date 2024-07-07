package com.strongclone.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.strongclone.app.ui.theme.StrongCloneTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StrongCloneTheme {
                MainScreen()
            }
        }
    }
}