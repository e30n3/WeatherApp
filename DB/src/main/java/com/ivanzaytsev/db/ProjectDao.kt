package com.ivanzaytsev.db

import androidx.room.Dao
import androidx.room.Query
import com.ivanzaytsev.db.Project
import com.ivanzaytsev.db.utils.BaseDao

@Dao
interface ProjectDao: BaseDao<Project> {
    @Query("SELECT * from project WHERE project_id=:project_id")
    fun get(project_id: Int): Project


    @Query("SELECT * from project")
    fun getAll(): List<Project>
}