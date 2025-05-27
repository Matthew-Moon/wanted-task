package com.wanted.task.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val WantedColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = White,
    surface = White,
    surfaceVariant = White,
    onBackground = Black,
    onSurface = Black,
    onSurfaceVariant = Black
)


@Composable
fun WantedTaskTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = WantedColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}