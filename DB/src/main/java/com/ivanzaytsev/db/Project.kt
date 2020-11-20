package com.ivanzaytsev.db

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(
    tableName = "project",
    indices = [Index("project_id", unique = true)]
) //имя таблицы
@Parcelize
class Project(

    @ColumnInfo(name = "time", index = true)
    var creation_time: Date = Date(),

    @ColumnInfo(name = "latlong", index = true)
    var latlong: String = "",

    @ColumnInfo(name = "description", index = true)
    var description: String = "",

    @ColumnInfo(name = "main", index = true)
    var main: String = "",

    @ColumnInfo(name = "feels", index = true)
    var feels: String = "",

    @ColumnInfo(name = "city", index = true)
    var city: String = "",

    @ColumnInfo(name = "details", index = true)
    var details: String = ""

) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "project_id")
    var project_id: Int = 0 //первичный ключ

}