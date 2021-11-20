package com.praveen.astro.ui.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.praveen.astro.ui.theme.FontLato

@Composable
fun ScreenTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = title,
        style = TextStyle(
            fontFamily = FontLato,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
    )
}
