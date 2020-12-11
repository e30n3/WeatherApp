package com.example.myapplication.adapters

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.ivanzaytsev.db.Project
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

import androidx.core.view.isGone


import kotlinx.android.synthetic.main.item_save.*
import kotlinx.android.synthetic.main.item_save.view.*


class RecyclerViewAdapter(private var projectList: List<Project>, private val resources: Resources) :
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    init {
        projectList = projectList.sortedByDescending { it.creation_time }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_save, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(projectList[position])
    }

    override fun getItemCount(): Int = projectList.size

    inner class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bind(project: Project) {
            val projectData = getProjectData(project)
            containerView.imageWeather.setImageDrawable(projectData.drawableRes)
            var str = "${projectData.temperature}℃, ${projectData.description}"
            containerView.textMainInfo.text = str
            val sm =
                    SimpleDateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT)
            str = "${sm.format(project.creation_time)}, ${projectData.city} "
            containerView.textDate.text = str
        }
    }

    fun getProjectData(project: Project): ProjectData {
        val jsonObject = JSONObject(project.content)
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

}
