package com.example.amazing_calculator.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazing_calculator.domain.HistoryRepository
import com.example.amazing_calculator.domain.entity.HistoryItem
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository
): ViewModel() {

    private val _closeWithResult = MutableLiveData<HistoryItem>()
    val closeWithResult: LiveData<HistoryItem> = _closeWithResult


    private val _historyItemsState = MutableLiveData<List<HistoryItem>>()
    val historyItemsState : LiveData<List<HistoryItem>> = _historyItemsState

    init{
        viewModelScope.launch{
            _historyItemsState.value = historyRepository.getAll()
        }
    }

    fun onItemClicked(historyItem: HistoryItem){
        _closeWithResult.value = historyItem
    }
}