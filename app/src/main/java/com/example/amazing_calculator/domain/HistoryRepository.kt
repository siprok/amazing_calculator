package com.example.amazing_calculator.domain

import com.example.amazing_calculator.data.db.history.HistoryItemEntity
import com.example.amazing_calculator.domain.entity.HistoryItem

interface HistoryRepository {
   suspend fun add(historyItem: HistoryItem)

   suspend fun getAll(): List<HistoryItem>
}