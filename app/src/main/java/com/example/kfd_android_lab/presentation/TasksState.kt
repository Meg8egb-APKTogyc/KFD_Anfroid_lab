package com.example.kfd_android_lab.presentation

import com.example.kfd_android_lab.data.TaskEntity

data class TasksState(
    val query: String = "",
    val tasks: List<TaskEntity> = emptyList(),
    val isLoading: Boolean = false,
)
