package com.epitkane19.todoroom.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.epitkane19.todoroom.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Jos instanssi on jo olemassa, palauta se suoraan
            // synchronized = vain yksi säie kerrallaan voi luoda tietokannan
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,  // Application context (elää koko sovelluksen ajan)
                    AppDatabase::class.java,     // Tietokantaluokka
                    "todo_db"               // Tiedoston nimi: /databases/app_database
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


