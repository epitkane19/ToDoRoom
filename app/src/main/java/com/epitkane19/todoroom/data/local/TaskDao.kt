package com.epitkane19.todoroom.data.local

import androidx.room.*
import com.epitkane19.todoroom.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE done = :done")
    fun getTasksByDone(done: Boolean): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    fun getTasksSortedByDueDate(): Flow<List<TaskEntity>>

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}
