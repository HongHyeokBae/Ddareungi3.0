package com.example.ddareungi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ddareungi.data.dao.BookmarkDao
import com.example.ddareungi.data.dao.BookmarkStationDao

@Database(entities = [StationRe::class, BookmarkStation::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun bookmarkStationDao() : BookmarkStationDao

    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        private var sInstance: AppDatabase? = null

        const val DATABASE_NAME = "bookmark-db"

        fun getInstance(context: Context): AppDatabase {
            return sInstance ?: synchronized(this) {
                sInstance ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                    .build()
            }
        }
    }


}