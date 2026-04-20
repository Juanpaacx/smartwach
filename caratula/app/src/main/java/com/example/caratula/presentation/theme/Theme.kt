package com.example.caratula.presentation.theme


import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme





@Composable
fun CaratulaTheme(content: @Composable () -> Unit
) {
    /**
     * Empty theme to customize for your app.
     * See: https://developer.android.com/jetpack/compose/designsystems/custom
     */
    MaterialTheme(
        colors = WearAppColorPalette,
        typography = WearAppTypography,
        content = content
    )
}