package com.example.amazing_calculator.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.amazing_calculator.data.db.history.HistoryItemDao
import com.example.amazing_calculator.data.db.history.HistoryItemEntity

@Database(
    entities = [HistoryItemEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MainDatabase : RoomDatabase(){
    abstract val historyItemDao: HistoryItemDao
    companion object{
        fun create(context: Context): MainDatabase =
             Room.databaseBuilder(context,
                 MainDatabase::class.java,
                 "main_database")
                .build()

    }
}