package com.example.myapplication
//
//import android.graphics.drawable.DrawableContainer
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.myapplication.model.ProjectData
//import org.junit.Assert
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.util.*
//
//@RunWith(MockitoJUnitRunner.class)
//class MockitoTest {
//
//    private val projects = listOf(
//            ProjectData(
//                    date = Date(),
//                    main = "f1",
//                    description = " ",
//                    temperature = "20",
//                    pressure = " ",
//                    humidity = " ",
//                    windSpeed = " ",
//                    feelsLike = " ",
//                    city = " ",
//                    drawableRes = DrawableContainer()
//            ),
//            ProjectData(
//                    date = Date(),
//                    main = "f2",
//                    description = " ",
//                    temperature = "20",
//                    pressure = " ",
//                    humidity = " ",
//                    windSpeed = " ",
//                    feelsLike = " ",
//                    city = " ",
//                    drawableRes = DrawableContainer()
//            ),
//            ProjectData(
//                    date = Date(),
//                    main = "f1",
//                    description = " ",
//                    temperature = "1",
//                    pressure = " ",
//                    humidity = " ",
//                    windSpeed = " ",
//                    feelsLike = " ",
//                    city = " ",
//                    drawableRes = DrawableContainer()
//            )
//
//    )
//    private val emptyList = emptyList<ProjectData>()
//
//    @Test
//    fun hasTrue() {
//        Assert.assertEquals(
//                true,
//                projects.hasTempLowerThan(20.0)
//        )
//
//    }
//
//    @Test
//    fun hasFalse() {
//        Assert.assertEquals(
//                false,
//                projects.hasTempLowerThan(0.0)
//        )
//    }
//
//    @Test
//    fun hasNull() {
//        Assert.assertEquals(
//                null,
//                emptyList.hasTempLowerThan(20.0)
//        )
//    }
//
//}