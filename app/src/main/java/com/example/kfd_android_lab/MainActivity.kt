package com.example.kfd_android_lab

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kfd_android_lab.data.FakeTasksRepository
import com.example.kfd_android_lab.presentation.AppNavHost
import com.example.kfd_android_lab.presentation.SettingsViewModel
import com.example.kfd_android_lab.presentation.SettingsViewModelFactory

val Context.dataStore by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = FakeTasksRepository()

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(dataStore)
            )
            val darkTheme by settingsViewModel.darkTheme.collectAsState()

            ComposeadvancedTheme(darkTheme = darkTheme) {
                AppNavHost(
                    repository = repository,
                    darkTheme = darkTheme,
                    onThemeChanged = { settingsViewModel.setDarkTheme(it) }
                )
            }
        }
    }
}