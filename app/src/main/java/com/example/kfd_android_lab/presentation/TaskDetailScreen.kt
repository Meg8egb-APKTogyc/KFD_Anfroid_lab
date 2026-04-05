package com.example.kfd_android_lab.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kfd_android_lab.data.TaskEntity

@Composable
fun TaskDetailRoute(id: String, viewModel: TasksViewModel, onBack: () -> Unit, modifier: Modifier = Modifier) {
    var task by remember { mutableStateOf<TaskEntity?>(null) }

    LaunchedEffect(id) {
        task = viewModel.getTask(id)
    }
    TaskDetailScreen(
        task,
        onBack = onBack,
        onSave = { id, isDone -> viewModel.saveTask(id, isDone) }
    )
}

@Composable
fun TaskDetailScreen(
    task: TaskEntity?,
    onBack: () -> Unit,
    onSave: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (task == null) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(text = task.id, style = MaterialTheme.typography.titleLarge)
                    Text(text = task.title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = if (task.isDone) "Done!" else "Not Done :(",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = { onSave(task.id, !task.isDone) }) {
                            Text(if (task.isDone) "make undone :(" else "make done :)")
                        }
                        Button(onBack) {
                            Text("go back")
                        }
                    }
                }
            }
        }

    }
}