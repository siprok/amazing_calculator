package com.example.amazing_calculator.presentation.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.amazing_calculator.R
import com.example.amazing_calculator.databinding.HistoryActivityBinding

class HistoryActivity: AppCompatActivity() {

    private val viewBinding by viewBinding(HistoryActivityBinding::bind)
    private val viewModel by viewModels<HistoryViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity)
        val historyAdapter = HistoryAdapter(viewModel::onItemClicked)
        with(viewBinding.historyList){
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }

        viewModel.closeWithResult.observe(this){ state ->

        }

        viewModel.historyItemsState.observe(this){state ->
            historyAdapter.setData(state)
        }
    }
}