package com.example.amazing_calculator.presentation.main

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazing_calculator.domain.HistoryRepository
import com.example.amazing_calculator.domain.SettingsDao
import com.example.amazing_calculator.domain.entity.HistoryItem
import kotlinx.coroutines.launch
import java.lang.Math.pow
import java.lang.Math.round
import kotlin.math.abs
import kotlin.math.pow


class MainViewModel(
    private val settingsDao: SettingsDao,
    private val historyRepository: HistoryRepository
): ViewModel() {

    private var outputExpression: String = ""

    private val _outputExpressionState = MutableLiveData<String>(outputExpression)
    private val _sliderRoundState = MutableLiveData<Int>()
    private val _sliderVibrateState = MutableLiveData<Int>()

    val outputExpressionState : LiveData<String> = _outputExpressionState
    val sliderRoundState : LiveData<Int> = _sliderRoundState
    val sliderVibrateState : LiveData<Int> = _sliderVibrateState

    init{
        viewModelScope.launch{
            _sliderRoundState.value = settingsDao.getSliderRoundValue()
            _sliderVibrateState.value = settingsDao.getVibrateDuration()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun onNumberClick(number: Int, field: EditText) {
        val position = field.selectionStart
        val curText = field.getText().toString()
        var message = number.toString()
        if (position > 0)
        {
            if (curText[position-1] == '√') {
                message = "($message"
            }
            if (curText[position-1] == ')') {
                message = "*$message"
            }
        }
        if (position < field.getText().lastIndex)
        {
            listOf('(', '√').forEach { symbol ->
                if (curText[position] == symbol) {
                    message += "*"
                }
            }
        }
        field.getText().insert(position, message)
        field.setSelection(position + message.length)
    }

    fun onSymbolClick(symbol: String, field: EditText) {
        val position = field.selectionStart
        var isOk = true
        if (position > 0) {
            listOf('+', '-', '/', '*', '^').forEach { symbol ->
                if (field.getText()[position-1] == symbol)
                {
                    isOk = false
                }
            }
        }
        if (isOk) {
            field.getText().insert(position, symbol)
            field.setSelection(position + 1)
        }
    }

    fun onLeftBracketClick(field: EditText){
        val position = field.selectionStart
        var message = "("
        var shift = 1
        if (position > 0){
            if (field.getText()[position - 1] == ')'){
                message = "*$message"
                shift++
            }
        }
        if (position < field.getText().lastIndex) {
            listOf('+', '-', '/', '*', '^').forEach { symbol ->
                if (field.getText()[position] == symbol) {
                    message += ")"
                }
            }
            if (field.getText()[position] != ')') {
                message += ")"
            }
        } else {
            message += ")"
        }
        field.getText().insert(position, message)
        field.setSelection(position + shift)
    }

    fun onRightBracketClick(field: EditText){
        val position = field.selectionStart
        var message = ")"
        var shift = 1
        if (position <= field.getText().lastIndex){
            if (field.getText()[position] == '('){
                message += "*"
                shift++
            }
            if (field.getText()[position] == ')')
            {
                message = ""
            }
        }
        field.setText(field.getText().insert(position, message))
        field.setSelection(position + shift)
    }

    fun onEqualClick(field: EditText) {
        viewModelScope.launch{
            _sliderRoundState.value = settingsDao.getSliderRoundValue()
        }
        val formattingString = "%." + _sliderRoundState.value.toString() + "f"
        val result = evaluatedFormatter(evaluate(field.getText().toString()))
        if (result.toFloatOrNull() == null)
            _outputExpressionState.value = result
        else {
            val pred: Boolean? = sliderRoundState.value?.let{abs(result.toFloat() - result.toFloat().toInt()) > 10f.pow(-it)}
            if(pred != null && pred)
            {
                _outputExpressionState.value = "= " + formattingString.format(result.toFloat())
                viewModelScope.launch{
                    historyRepository.add(HistoryItem(field.getText().toString(), _outputExpressionState.value!!))
                }
            }
            else
            {
                _outputExpressionState.value = "= " + result.toFloat().toInt().toString()
                viewModelScope.launch{
                    historyRepository.add(HistoryItem(field.getText().toString(), _outputExpressionState.value!!))
                }
            }
        }
    }

    fun onBackspaceClick(field: EditText) {
        var new_text = field.getText().toString()
        val position = field.selectionStart
        var shift = 1
        if (new_text.length > 0 && position > 0) {
            if (new_text.length > 1 && position > 1) {
                if (field.getText().toString().substring(position - 2, position) == "()"){
                    shift++
                }
            }
            new_text = new_text.removeRange(position - shift, position)
            field.setText(new_text)
            field.setSelection(position-shift)
        }
    }

    fun onCleanerMemoryClick() {

    }

    fun onCleanerClick(field: EditText) {
        field.setText("")
        _outputExpressionState.value = ""
    }

    fun onSqrtClick(field: EditText) {
        val position = field.selectionStart
        val curText = field.getText().toString()
        var message = "√"
        var shift = 1
        if (position > 0) {  // проверка на символы слева
            generateSequence('0'){ if (it < '9') it + 1 else ')' }.take(11).forEach{ number ->
                if (curText.get(position - 1) == number) {
                    message = "*$message"
                    shift++
                }
            }
        }
        if (position < curText.lastIndex) { // проверка на символы справа
            // проверка на числа справа
            generateSequence('0'){ it + 1 }.take(10).forEach{ number ->
                if (curText[position] == number) {
                    message += "("
                    shift++
                }
            }

            if(message[message.lastIndex] != '('){ // справа нет числа
                // проверка на знак операции справа
                listOf('*', '/', '+', '-', '√', '^').forEach{ symbol ->
                    if (curText[position] == symbol) {
                        message += "()"
                        shift ++
                    }
                }
            }

        }
        else{
            message += "()"
            shift++
        }
        field.getText().insert(position, message)
        field.setSelection(position + shift)
    }

    fun onMemoryClick() {

    }

    fun onSettingsClick() {
    }

    fun onStart() {
        viewModelScope.launch{
            _sliderRoundState.value = settingsDao.getSliderRoundValue()
            _sliderVibrateState.value = settingsDao.getVibrateDuration()
        }
    }
    //TODO settings history
}