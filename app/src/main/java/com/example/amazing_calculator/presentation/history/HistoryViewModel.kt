package com.example.amazing_calculator.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amazing_calculator.domain.entity.HistoryItem

class HistoryViewModel: ViewModel() {

    private val historyItems: List<HistoryItem> = listOf(
        HistoryItem("2+2", "= 4"),
        HistoryItem("2*3", "= 6"),
        HistoryItem("2+2", "= 4"),
        HistoryItem("2*3", "= 6"),
        HistoryItem("2+2", "= 4"),
        HistoryItem("2*3", "= 6"),
        HistoryItem("2+2", "= 4"),
        HistoryItem("2*3", "= 6"),
        HistoryItem("2+2", "= 4"),
        HistoryItem("2*3", "= 6")
    )

    private val _historyItemsState = MutableLiveData<List<HistoryItem>>()
    val historyItemsState : LiveData<List<HistoryItem>> = _historyItemsState

    init{
        _historyItemsState.value = historyItems
    }

    fun onItemClicked(historyItem: HistoryItem){

    }
}