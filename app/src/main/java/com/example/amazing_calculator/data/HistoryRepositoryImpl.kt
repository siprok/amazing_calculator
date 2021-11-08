package com.example.amazing_calculator.data

import com.example.amazing_calculator.data.db.history.HistoryItemDao
import com.example.amazing_calculator.data.db.history.HistoryItemEntity
import com.example.amazing_calculator.domain.HistoryRepository
import com.example.amazing_calculator.domain.entity.HistoryItem

class HistoryRepositoryImpl(
    private val historyItemDao: HistoryItemDao
) : HistoryRepository {

    override suspend fun add(historyItem: HistoryItem) {
        historyItemDao.insert(historyItem.toHistoryItemEntity())
    }

    override suspend fun getAll(): List<HistoryItem> {
        return historyItemDao.getAll().map{it.toHistoryItem()}
    }

    private fun HistoryItem.toHistoryItemEntity() = HistoryItemEntity(
        id = 0,
        expression = expression,
        result = result,
    )

    private fun HistoryItemEntity.toHistoryItem() = HistoryItem(
        expression = expression,
        result = result,
    )
}