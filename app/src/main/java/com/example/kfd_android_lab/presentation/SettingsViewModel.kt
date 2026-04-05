package com.example.kfd_android_lab.presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _darkTheme = MutableStateFlow(false)
    val darkTheme = _darkTheme.asStateFlow()

    init {
        viewModelScope.launch {
            val darkThemeKey = booleanPreferencesKey("dark_theme")
            dataStore.data.collect { preferences ->
                _darkTheme.value = preferences[darkThemeKey] ?: false
            }
        }
    }

    fun setDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            val darkThemeKey = booleanPreferencesKey("dark_theme")
            dataStore.edit { preferences ->
                preferences[darkThemeKey] = isDark
            }
        }
    }
}