package com.example.amazing_calculator.presentation.settings

import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazing_calculator.R
import com.example.amazing_calculator.domain.SettingsDao
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsDao: SettingsDao
): ViewModel() {
    private val _sliderRoundState = MutableLiveData<Int>(R.integer.round_default)
    private val _sliderVibrateState = MutableLiveData<Int>(R.integer.vibrate_duration_default)

    val sliderRoundState : LiveData<Int> = _sliderRoundState
    val sliderVibrateState : LiveData<Int> = _sliderVibrateState

    val sliderRoundListener : SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
            _sliderRoundState.value = progress
        }

        override fun onStartTrackingTouch(seekbar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekbar: SeekBar?) {

        }
    }
    val sliderVibrateListener : SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
            _sliderVibrateState.value = progress
        }

        override fun onStartTrackingTouch(seekbar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekbar: SeekBar?) {

        }
    }
    init{
        viewModelScope.launch{
            _sliderRoundState.value = settingsDao.getSliderRoundValue()
            _sliderVibrateState.value = settingsDao.getVibrateDuration()
        }
    }

    fun onDestroy() {
        viewModelScope.launch{
            sliderRoundState.value?.let { settingsDao.setSliderRoundValue(it) }
            sliderVibrateState.value?.let { settingsDao.setVibrateDuration(it) }
        }
    }
}