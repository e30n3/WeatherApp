package com.example.myapplication

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.model.IAuthorization
import com.example.myapplication.model.ProjectData
import com.ivanzaytsev.db.Project
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.properties.Delegates

fun Project.toProjectData(resources: Resources): ProjectData {
    val jsonObject = JSONObject(this.content)
    val weatherData = jsonObject.getString("weather")
    val mainTemperature = jsonObject.getString("main")
    val wind = jsonObject.getString("wind")
    val cityName = jsonObject.getString("name")
    Log.i("weatherData", weatherData)
    val array = JSONArray(weatherData)
    val date = Date()
    var main = ""
    var description = ""
    var icon = ""
    val temperature: String
    val feelsLike: String
    val pressure: String
    val humidity: String
    val windSpeed: String
    for (i in 0 until array.length()) {
        val weatherPart = array.getJSONObject(i)
        main = weatherPart.getString("main")
        description = weatherPart.getString("description")
        icon = weatherPart.getString("icon")
    }
    val mainPart = JSONObject(mainTemperature)
    temperature = mainPart.getString("temp")
    feelsLike = mainPart.getString("feels_like")
    pressure = mainPart.getString("pressure")
    humidity = mainPart.getString("humidity")
    val windPart = JSONObject(wind)
    windSpeed = windPart.getString("speed")
    var fIcon = icon
    val drawableRes = when (icon) {
        "01d" -> ResourcesCompat.getDrawable(resources, R.drawable.l01d, null)
        "01n" -> ResourcesCompat.getDrawable(resources, R.drawable.l01n, null)
        "02d" -> ResourcesCompat.getDrawable(resources, R.drawable.l02d, null)
        "02n" -> ResourcesCompat.getDrawable(resources, R.drawable.l02n, null)
        "03d" -> ResourcesCompat.getDrawable(resources, R.drawable.l03d, null)
        "03n" -> ResourcesCompat.getDrawable(resources, R.drawable.l03n, null)
        "04d" -> ResourcesCompat.getDrawable(resources, R.drawable.l04d, null)
        "04n" -> ResourcesCompat.getDrawable(resources, R.drawable.l04n, null)
        "09d" -> ResourcesCompat.getDrawable(resources, R.drawable.l09d, null)
        "10n" -> ResourcesCompat.getDrawable(resources, R.drawable.l10n, null)
        "10d" -> ResourcesCompat.getDrawable(resources, R.drawable.l10d, null)
        "11n" -> ResourcesCompat.getDrawable(resources, R.drawable.l11n, null)
        "11d" -> ResourcesCompat.getDrawable(resources, R.drawable.l11d, null)
        "13n" -> ResourcesCompat.getDrawable(resources, R.drawable.l13n, null)
        "13d" -> ResourcesCompat.getDrawable(resources, R.drawable.l13d, null)
        "50n" -> ResourcesCompat.getDrawable(resources, R.drawable.l50n, null)
        "50d" -> ResourcesCompat.getDrawable(resources, R.drawable.l50d, null)
        else -> throw Exception("this icon not found")
    }
    return ProjectData(date, main, description, temperature, pressure, humidity, windSpeed, feelsLike, cityName, drawableRes!!)
}

infix fun ProjectData.equalsByTemperature(projectData: ProjectData) =
        if (this.temperature.contentEquals(projectData.temperature)) {
            Log.i("Project.toProjectData", "Equals")
            true
        } else {
            Log.i("Project.toProjectData", "NotEquals")
            false
        }

fun List<ProjectData>.hasTempLowerThan(temperature: Double): Boolean? {
    if (this.isEmpty()) {
        Log.i("List<ProjectData>", "List is empty")
        return null
    } else {
        this.forEach {
            if (it.temperature < temperature.toString()) return true
        }
    }
    return false
}

fun mock(): IAuthorization {
    val mock: IAuthorization? = null
    return mock!!
}

fun whenever(any: Any){

}
fun verify(any: Any){

}