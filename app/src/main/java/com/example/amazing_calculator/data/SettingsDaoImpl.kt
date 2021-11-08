package com.example.amazing_calculator.data

import android.content.SharedPreferences
import com.example.amazing_calculator.R
import com.example.amazing_calculator.domain.SettingsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsDaoImpl(
    private val preferences: SharedPreferences
): SettingsDao {
    override suspend fun getSliderRoundValue(): Int = withContext(Dispatchers.IO){
            preferences.getInt(SLIDER_ROUND_VALUE_KEY, R.integer.round_default)
        }


    override suspend fun setSliderRoundValue(newRoundValue: Int) = withContext(Dispatchers.IO){
        preferences.edit().putInt(SLIDER_ROUND_VALUE_KEY, newRoundValue).apply()
    }

    override suspend fun getVibrateDuration():Int = withContext(Dispatchers.IO){
        preferences.getInt(VIBRATE_DURATION_KEY, R.integer.vibrate_duration_default)
    }

    override suspend fun setVibrateDuration(newVibrateDuration: Int) = withContext(Dispatchers.IO) {
        preferences.edit().putInt(VIBRATE_DURATION_KEY, newVibrateDuration).apply()
    }
    companion object {
        private const val SLIDER_ROUND_VALUE_KEY = "SLIDER_ROUND_VALUE_KEY"
        private const val VIBRATE_DURATION_KEY = "VIBRATE_DURATION_KEY"
    }
}