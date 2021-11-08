package com.example.amazing_calculator.presentation.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.amazing_calculator.R
import com.example.amazing_calculator.databinding.SettingsActivityBinding
import com.example.amazing_calculator.di.SettingsDaoProvider


class SettingsActivity: AppCompatActivity() {

    private val viewBinding by viewBinding(SettingsActivityBinding::bind)
    private val viewModel  by viewModels<SettingsViewModel>{
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SettingsViewModel(SettingsDaoProvider.getDao(this@SettingsActivity))as T
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        viewBinding.sliderRound.setOnSeekBarChangeListener (viewModel.sliderRoundListener)
        viewBinding.sliderVibrate.setOnSeekBarChangeListener (viewModel.sliderVibrateListener)

        viewModel.sliderRoundState.observe(this){state ->
            viewBinding.sliderRound.progress = state
            if (state > 4 || state == 0) {
                viewBinding.sliderRoundLabel.text = "Точность: $state знаков поле запятой"
            } else if (state > 1) {
                viewBinding.sliderRoundLabel.text = "Точность: $state знака поле запятой"
            } else{
                viewBinding.sliderRoundLabel.text = "Точность: $state знак поле запятой"
            }
        }
        viewModel.sliderVibrateState.observe(this){state ->
            viewBinding.sliderVibrate.progress = state
        }
        viewBinding.buttonApply.setOnClickListener {
            viewModel.onDestroy()
            finish()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

}

