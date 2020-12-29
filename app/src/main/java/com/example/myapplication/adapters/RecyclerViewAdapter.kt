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
import com.example.myapplication.model.ProjectData
import com.example.myapplication.toProjectData


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
            val projectData = project.toProjectData(resources)
            containerView.imageWeather.setImageDrawable(projectData.drawableRes)
            var str = "${projectData.temperature}℃, ${projectData.description}"
            containerView.textMainInfo.text = str
            val sm =
                    SimpleDateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT)
            str = "${sm.format(project.creation_time)}, ${projectData.city} "
            containerView.textDate.text = str
        }
    }


}
