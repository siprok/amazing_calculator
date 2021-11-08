package com.example.amazing_calculator.domain

interface SettingsDao {
    /**
     * Устанавливает количество знаков после запятой при отображении результата
     */
    suspend fun setSliderRoundValue(newRoundValue: Int)

    /**
     * Получает количество знаков после запятой при отображении результата
     */
    suspend fun getSliderRoundValue(): Int

    /**
     * Устанавливает длительность вибрации кнопок при нажатии
     */
    suspend fun setVibrateDuration(newVibrateDuration: Int)

    /**
     * Получает длительность вибрации кнопок при нажатии
     */
    suspend fun getVibrateDuration(): Int
}