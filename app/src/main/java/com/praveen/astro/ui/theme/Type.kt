package com.praveen.astro.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.praveen.astro.R

val FontLato = FontFamily(
    Font(R.font.lato)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontLato,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = LightGrey
    ),

    body2 = TextStyle(
        fontFamily = FontLato,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = LightGrey
    ),

    h6 = TextStyle(
        fontFamily = FontLato,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp,
        color = Color.Black
    ),

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */

)
