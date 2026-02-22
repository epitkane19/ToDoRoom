package com.epitkane19.todoroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.epitkane19.todoroom.ui.theme.ToDoRoomTheme
import com.epitkane19.todoroom.view.HomeScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epitkane19.todoroom.data.local.AppDatabase
import com.epitkane19.todoroom.data.repository.TaskRepository
import com.epitkane19.todoroom.viewmodel.TaskViewModel
import com.epitkane19.todoroom.view.CalendarScreen
import androidx.lifecycle.ViewModelProvider




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getDatabase(this)
        val repo = TaskRepository(db.taskDao())


        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val taskViewModel: TaskViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                            @Suppress("UNCHECKED_CAST")
                            return TaskViewModel(repo) as T
                        }
                        throw IllegalArgumentException("Unknown ViewModel class")
                    }
                }
            )

            ToDoRoomTheme {
                NavHost(
                    navController = navController,
                    startDestination = "ROUTE_HOME"
                ) {
                    composable("ROUTE_HOME") {
                        HomeScreen(
                            taskViewModel = taskViewModel,
                            navigateToCalendar = { navController.navigate("ROUTE_CALENDAR") },
                        )
                    }
                    composable("ROUTE_CALENDAR") {
                        CalendarScreen(
                            taskViewModel = taskViewModel,
                            navigateToBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoRoomTheme {
        Greeting("Android")
    }
}