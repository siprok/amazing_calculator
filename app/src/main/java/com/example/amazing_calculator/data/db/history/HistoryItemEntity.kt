package com.example.amazing_calculator.data.db.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_name_entity")
class HistoryItemEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val expression: String,
    val result: String
)
