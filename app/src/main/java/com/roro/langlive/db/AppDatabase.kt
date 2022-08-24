package com.roro.langlive.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.roro.langlive.model.MovieModel

@Database(entities = [MovieModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,
                        "movies").build()
                INSTANCE = instance

                instance
            }
        }
    }
}