package com.example.amazing_calculator.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.amazing_calculator.R
import com.example.amazing_calculator.databinding.MainActivityBinding
import com.example.amazing_calculator.di.SettingsDaoProvider
import com.example.amazing_calculator.presentation.history.HistoryActivity
import com.example.amazing_calculator.presentation.settings.SettingsActivity
import com.example.amazing_calculator.presentation.settings.SettingsViewModel
import java.lang.Math.round


class MainActivity : AppCompatActivity()
{
    private val viewBinding by viewBinding(MainActivityBinding::bind)
    private val viewModel  by viewModels<MainViewModel>{
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(SettingsDaoProvider.getDao(this@MainActivity))as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        // Отключим встроенную клаиатуру
        viewBinding.expressionInput.apply {
            showSoftInputOnFocus = false
        }

        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        listOf(
            viewBinding.buttonZero,
            viewBinding.buttonOne,
            viewBinding.buttonTwo,
            viewBinding.buttonThree,
            viewBinding.buttonFour,
            viewBinding.buttonFive,
            viewBinding.buttonSix,
            viewBinding.buttonSeven,
            viewBinding.buttonEight,
            viewBinding.buttonNine
        ).forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
                viewModel.onNumberClick(index, viewBinding.expressionInput)
            }
        }

        viewBinding.buttonEqual.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onEqualClick(viewBinding.expressionInput)
        }
        viewBinding.buttonBackspace.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onBackspaceClick(viewBinding.expressionInput)
        }
        viewBinding.buttonCleaner.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onCleanerClick(viewBinding.expressionInput)
        }
        viewBinding.buttonCleanMemory.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onCleanerMemoryClick()
        }
        viewBinding.buttonMemory.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onMemoryClick()
        }
        viewBinding.buttonPoint.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onSymbolClick(viewBinding.buttonPoint.text.toString(), viewBinding.expressionInput)
        }
        viewBinding.buttonPlus.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onSymbolClick(viewBinding.buttonPlus.text.toString(), viewBinding.expressionInput)
        }
        viewBinding.buttonMinus.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onSymbolClick(viewBinding.buttonMinus.text.toString(), viewBinding.expressionInput)
        }
        viewBinding.buttonMul.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onSymbolClick(viewBinding.buttonMul.text.toString(), viewBinding.expressionInput)
        }
        viewBinding.buttonDiv.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onSymbolClick(viewBinding.buttonDiv.text.toString(),viewBinding.expressionInput)
        }
        viewBinding.buttonSqrt.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onSqrtClick(viewBinding.expressionInput)
        }
        viewBinding.buttonPow.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onSymbolClick(viewBinding.buttonPow.text.toString(), viewBinding.expressionInput)
        }
        viewBinding.buttonLeftBracket.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onLeftBracketClick(viewBinding.expressionInput)
        }
        viewBinding.buttonRightBracket.setOnClickListener {
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            viewModel.onRightBracketClick(viewBinding.expressionInput)
        }

        viewBinding.buttonSettigns.setOnClickListener{
            viewModel.sliderVibrateState.value?.let{vibrator.vibrate(it.toLong() + 1)}
            showSettings()
        }

        viewBinding.buttonHistory.setOnClickListener {
            showHistory()
        }

        viewModel.outputExpressionState.observe(this) {state ->
            viewBinding.expressionOutput.text = state
        }

        viewModel.sliderRoundState.observe(this) {state ->
            val result = viewBinding.expressionOutput.text.toString()
            val formattingString = "%." + state.toString() + "f"
            if (result.toFloatOrNull() == null)
            {}
            else if(result.toIntOrNull() == null)
                viewBinding.expressionOutput.text = "= " + formattingString.format(result.toFloat())
            else
                viewBinding.expressionOutput.text = "= " + result.toInt().toString()
        }

    }

    fun showSettings(){
        /*
        val view = getLayoutInflater().inflate(R.layout.settings_activity, null)
        AlertDialog.Builder(this)
            .setView(view)
            .show()
        */
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    fun showHistory()
    {
        startActivity(Intent(this, HistoryActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }
}