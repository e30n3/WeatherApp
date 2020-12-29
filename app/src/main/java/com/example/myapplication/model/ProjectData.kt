package com.example.myapplication.model

import android.graphics.drawable.Drawable
import java.util.*

data class ProjectData(
        val date: Date,
        val main: String,
        val description: String,
        val temperature: String,
        val pressure: String,
        val humidity: String,
        val windSpeed: String,
        val feelsLike: String,
        val city: String,
        val drawableRes: Drawable
)
