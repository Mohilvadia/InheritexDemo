package com.inheritex.demo.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.inheritex.demo.data.NewsDBEntity
import com.inheritex.demo.data.NewsDataResponse

/// [NewsDB] This class is use for define database
@Database(entities = [NewsDBEntity::class], version = 1, exportSchema = false)
abstract class NewsDB : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object{
        private var instance : NewsDB? = null
        private var LOCK = Any()

        private fun buildDatabase(context: Context, newsDB: String): NewsDB {
            return Room.databaseBuilder(
                context.applicationContext,
                NewsDB::class.java,
                newsDB
            ).addCallback(object : RoomDatabase.Callback() {
                override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                    super.onDestructiveMigration(db)
                }

            }).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        }

        operator fun invoke(context: Context,NewsDBName: String) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context,NewsDBName).also { instance = it }
        }
    }
}