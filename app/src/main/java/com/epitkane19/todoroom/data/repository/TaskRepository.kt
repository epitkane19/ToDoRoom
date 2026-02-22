package com.epitkane19.todoroom.data.repository

import com.epitkane19.todoroom.data.local.TaskDao
import com.epitkane19.todoroom.model.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {

    val tasks: Flow<List<TaskEntity>> = dao.getAllTasks()

    suspend fun addTask(task: TaskEntity) {
        dao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        dao.updateTask(task)
    }

    suspend fun removeTask(task: TaskEntity) {
        dao.deleteTask(task)
    }

    fun getTasksByDone(done: Boolean): Flow<List<TaskEntity>> {
        return dao.getTasksByDone(done)
    }

    fun getTasksSortedByDueDate(): Flow<List<TaskEntity>> {
        return dao.getTasksSortedByDueDate()
    }
}
