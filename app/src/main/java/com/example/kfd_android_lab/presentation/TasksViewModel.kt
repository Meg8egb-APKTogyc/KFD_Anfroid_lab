package com.example.kfd_android_lab.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kfd_android_lab.data.FakeTasksRepository
import com.example.kfd_android_lab.data.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel(private val repository: FakeTasksRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(TasksState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val tasks = repository.getTasks()
            _uiState.update { it.copy(tasks = tasks, isLoading = false) }
        }
    }

    fun onQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    suspend fun getTask(id: String) : TaskEntity? {
        return repository.getTask(id)
    }

    fun saveTask(id: String, isDone: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.toggleDone(id, isDone)
            val tasks = repository.getTasks()
            _uiState.update { it.copy(tasks = tasks, isLoading = false) }
        }
    }
}