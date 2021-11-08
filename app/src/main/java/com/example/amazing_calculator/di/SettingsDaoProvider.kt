package com.example.amazing_calculator.di

import android.content.Context
import com.example.amazing_calculator.data.SettingsDaoImpl
import com.example.amazing_calculator.domain.SettingsDao

object SettingsDaoProvider {

    private  var dao: SettingsDao? = null

    fun getDao(context: Context): SettingsDao {
        return dao ?: SettingsDaoImpl(
            context.getSharedPreferences(
                "settings",
                Context.MODE_PRIVATE
            )
        ).also {
            dao = it
        }
    }
}