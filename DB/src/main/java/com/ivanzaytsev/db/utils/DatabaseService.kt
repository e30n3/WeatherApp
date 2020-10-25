package com.ivanzaytsev.db.utils

import com.ivanzaytsev.db.Project

object DatabaseService {
    fun getEntityClass(tableName: String): Class<*>? {
        return when (tableName) {
            "project" -> Project::class.java
            else -> Class.forName(tableName)
        }
    }

    fun getTableName(entityClass: Class<*>): String? {
        return when (entityClass) {
            Project::class.java -> "project"
            else -> entityClass.simpleName
        }
    }
}