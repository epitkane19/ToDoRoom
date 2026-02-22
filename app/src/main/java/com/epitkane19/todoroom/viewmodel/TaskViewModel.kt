package com.epitkane19.todoroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epitkane19.todoroom.model.TaskEntity
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import com.epitkane19.todoroom.data.repository.TaskRepository

class TaskViewModel(private val repo: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks
    private var currentFilter: FilterType = FilterType.ALL
    enum class FilterType { ALL, DONE, UNDONE, SORT_BY_DATE }


    init {
        viewModelScope.launch {
            repo.tasks.collect { list ->
                _tasks.value = list
            }
        }
    }


    fun addTask(task: TaskEntity) {
        viewModelScope.launch {
            repo.addTask(task)
        }
    }

    fun removeTask(task: TaskEntity) {
        viewModelScope.launch {
            repo.removeTask(task)
        }
    }


    fun toggleDone(task: TaskEntity) {
        viewModelScope.launch {
            val updated = task.copy(done = !task.done)
            repo.updateTask(updated)
            applyCurrentFilter()
        }
    }


    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repo.updateTask(task)
        }
    }

    fun showAll() {
        currentFilter = FilterType.ALL
        viewModelScope.launch {
            repo.tasks.collect { list ->
                _tasks.value = list
            }
        }
    }

    fun filterByDone(done: Boolean) {
        currentFilter = if (done) FilterType.DONE else FilterType.UNDONE
        viewModelScope.launch {
            repo.getTasksByDone(done).collect { list ->
                _tasks.value = list
            }
        }
    }

    fun sortByDueDate() {
        currentFilter = FilterType.SORT_BY_DATE
        viewModelScope.launch {
            repo.getTasksSortedByDueDate().collect { list ->
                _tasks.value = list
            }
        }
    }


    private fun applyCurrentFilter() {
        when (currentFilter) {
            FilterType.ALL -> showAll()
            FilterType.DONE -> filterByDone(true)
            FilterType.UNDONE -> filterByDone(false)
            FilterType.SORT_BY_DATE -> sortByDueDate()
        }
    }



}


