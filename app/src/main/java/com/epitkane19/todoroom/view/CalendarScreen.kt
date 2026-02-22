package com.epitkane19.todoroom.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epitkane19.todoroom.viewmodel.TaskViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import com.epitkane19.todoroom.model.TaskEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    taskViewModel: TaskViewModel,
    navigateToBack: () -> Unit
) {
    val tasks by taskViewModel.tasks.collectAsState()
    val groupedTasks = tasks.groupBy { it.dueDate ?: "No date" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") },
                navigationIcon = {
                    IconButton(onClick = navigateToBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            groupedTasks.forEach { (date, tasksForDate) ->
                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(tasksForDate) { task ->
                    CalendarTaskRow(task)
                }
            }
        }
    }
}

@Composable
fun CalendarTaskRow(task: TaskEntity) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(task.title, style = MaterialTheme.typography.bodyLarge)
        if (task.description.isNotBlank()) {
            Text(task.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

