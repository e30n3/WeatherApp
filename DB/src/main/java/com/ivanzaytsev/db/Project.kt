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

    @ColumnInfo(name = "content", index = true)
    var content: String = ""

) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "project_id")
    var project_id: Int = 0 //первичный ключ

}