package com.ivanzaytsev.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ivanzaytsev.db.utils.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Database(
    entities = [Project::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class appDB : RoomDatabase() {

    abstract fun projectDao(): ProjectDao

    companion object {
        @Volatile
        private var INSTANCE: appDB? = null
        private val lock = Any()
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)


        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): appDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDB::class.java,
                    "database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(appDBCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        fun setCreationTime(project: Project) {
            if (project.creation_time == null) {
                project.creation_time = Date()
            }
        }

        fun setEditTime(project: Project) {
            project.edit_time = Date()
        }


        fun getInstance(context: Context): appDB {

            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        appDB::class.java, "database"
                    )


                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE!!
            }
        }

        private class appDBCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(
                            database.projectDao()
                        )
                    }
                }
            }

            suspend fun populateDatabase(
                projectDao: ProjectDao
            ) {

            }
        }
    }


}

