package com.epitkane19.todoroom.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Checkbox
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.width
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.epitkane19.todoroom.viewmodel.TaskViewModel
import androidx.compose.foundation.clickable
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.getValue
import com.epitkane19.todoroom.model.TaskEntity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel = viewModel(),
    navigateToCalendar: () -> Unit
) {
    val tasks by taskViewModel.tasks.collectAsState()
    var editTask by remember { mutableStateOf<TaskEntity?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task List") },
                actions = {
                    IconButton(onClick = navigateToCalendar) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calendar"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // Uuden tehtävän lisäys
            Button(
                onClick = { showAddDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Task")
            }
            Spacer(Modifier.height(16.dp))

            // Suodatusnapit
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { taskViewModel.showAll() }) { Text("All") }
                Button(onClick = { taskViewModel.filterByDone(false) }) { Text("Undone") }
                Button(onClick = { taskViewModel.filterByDone(true) }) { Text("Done") }
            }

            Spacer(Modifier.height(16.dp))

            // Sort by date
            Button(
                onClick = { taskViewModel.sortByDueDate() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sort by date")
            }

            Spacer(Modifier.height(16.dp))

            // Tehtävälista
            LazyColumn {
                items(tasks) { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = task.done,
                                onCheckedChange = { taskViewModel.toggleDone(task) }
                            )

                            Spacer(Modifier.width(8.dp))

                            Column(
                                modifier = Modifier.clickable { editTask = task }
                            ) {
                                Text(
                                    text = task.title,
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Text(
                                    text = task.description,
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 2.dp)
                                )

                                Text(
                                    text = "Due: ${task.dueDate}",
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    editTask?.let { task ->
        DetailScreen(
            task = task,
            onDismiss = { editTask = null },
            taskViewModel = taskViewModel
        )
    }
    if (showAddDialog) {
        AddTaskDialog(
            onDismiss = { showAddDialog = false },
            onAddTask = { title, description, dueDate ->
                taskViewModel.addTask(
                    TaskEntity(
                        title = title,
                        description = description,
                        dueDate = dueDate,
                        done = false
                    )
                )
            }

        )
    }

}

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onAddTask: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Task") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                Spacer(Modifier.height(8.dp))

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                Spacer(Modifier.height(8.dp))

                TextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Due date (YYYY-MM-DD)") }
                )
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            onAddTask(title, description, dueDate)
                            onDismiss()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add")
                }
                Spacer(Modifier.width(16.dp))
                Button(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                    Text("Cancel")
                }
            }
        }
    )
}

