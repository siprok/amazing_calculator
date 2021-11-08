package com.example.amazing_calculator.di

import android.content.Context
import com.example.amazing_calculator.data.HistoryRepositoryImpl
import com.example.amazing_calculator.domain.HistoryRepository

object HistoryRepositoryProvider {
    private var repository: HistoryRepository? = null

    fun get(context: Context): HistoryRepository{
        return repository?: HistoryRepositoryImpl(DatabaseProvider.get(context).historyItemDao).also{ repository = it}
    }
}
