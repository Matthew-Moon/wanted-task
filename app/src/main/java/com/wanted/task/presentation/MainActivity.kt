package com.wanted.task.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.wanted.task.presentation.list.CompanyListScreen
import com.wanted.task.presentation.theme.WantedTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        setContent {
            WantedTaskTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CompanyListScreen()
                }
            }
        }
    }
}