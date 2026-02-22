package com.epitkane19.todoroom.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epitkane19.todoroom.model.TaskEntity
import com.epitkane19.todoroom.viewmodel.TaskViewModel

@Composable
fun DetailScreen(
    task: TaskEntity,
    onDismiss: () -> Unit,
    taskViewModel: TaskViewModel
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }
    var dueDate by remember { mutableStateOf(task.dueDate.orEmpty()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Task") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                TextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Due date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Row (
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        taskViewModel.updateTask(
                            task.copy(
                                title = title,
                                description = description,
                                dueDate = dueDate
                            )
                        )
                        onDismiss()
                    }
                ) {
                    Text("Save")
                }
                Spacer(Modifier.width(4.dp))
                Button(
                    onClick = {
                        taskViewModel.removeTask(task)
                        onDismiss()
                    }
                ) {
                    Text("Delete")
                }
                Spacer(Modifier.width(4.dp))
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        }
    )
}


