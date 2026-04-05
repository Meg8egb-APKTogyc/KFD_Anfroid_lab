package com.example.kfd_android_lab.data

data class TaskEntity(
    val id: String,
    val title: String,
    val isDone: Boolean = false,
)
