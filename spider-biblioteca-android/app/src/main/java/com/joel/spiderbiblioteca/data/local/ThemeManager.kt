package com.joel.spiderbiblioteca.data.local

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ThemeManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_IS_SIMBIONTE = "is_simbionte"

    private val _isSimbionte = MutableStateFlow(true)
    val isSimbionte: StateFlow<Boolean> = _isSimbionte.asStateFlow()

    fun init(context: Context) {
        _isSimbionte.value = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_IS_SIMBIONTE, true)
    }

    fun toggle(context: Context) {
        setSimbionte(context, !_isSimbionte.value)
    }

    fun setSimbionte(context: Context, value: Boolean) {
        _isSimbionte.value = value
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_IS_SIMBIONTE, value).apply()
    }
}
