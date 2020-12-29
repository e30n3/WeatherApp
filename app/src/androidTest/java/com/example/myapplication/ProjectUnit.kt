package com.example.myapplication

import android.graphics.drawable.DrawableContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.model.ProjectData
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class ProjectUnit {
    private val p1 = ProjectData(
            date = Date(),
            main = "f1",
            description = " ",
            temperature = "20",
            pressure = " ",
            humidity = " ",
            windSpeed = " ",
            feelsLike = " ",
            city = " ",
            drawableRes = DrawableContainer()
    )
    private val p2 = ProjectData(
            date = Date(),
            main = "f2",
            description = " ",
            temperature = "20",
            pressure = " ",
            humidity = " ",
            windSpeed = " ",
            feelsLike = " ",
            city = " ",
            drawableRes = DrawableContainer()
    )
    private val p3 = ProjectData(
            date = Date(),
            main = "f1",
            description = " ",
            temperature = "1",
            pressure = " ",
            humidity = " ",
            windSpeed = " ",
            feelsLike = " ",
            city = " ",
            drawableRes = DrawableContainer()
    )

    @Test
    fun equalsTrue() {
        Assert.assertEquals(true, p1 equalsByTemperature p2)

    }

    @Test
    fun equalsFalse() {
        Assert.assertEquals(false, p1 equalsByTemperature p3)
    }
}