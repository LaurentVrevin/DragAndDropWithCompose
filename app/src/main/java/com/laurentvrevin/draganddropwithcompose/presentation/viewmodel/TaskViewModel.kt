package com.laurentvrevin.draganddropwithcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentvrevin.draganddropwithcompose.domain.model.Task
import com.laurentvrevin.draganddropwithcompose.domain.usecase.AddTaskUseCase
import com.laurentvrevin.draganddropwithcompose.domain.usecase.DeleteTaskUseCase
import com.laurentvrevin.draganddropwithcompose.domain.usecase.GetTaskUseCase
import com.laurentvrevin.draganddropwithcompose.domain.usecase.UpdateTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTasksUseCase: UpdateTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            getTaskUseCase().collect { tasks ->
                _tasks.value = tasks
            }
        }
    }
     fun addTasks(task:Task){
        viewModelScope.launch {
            addTaskUseCase(task)
        }
    }
    fun deleteTasks(task:Task){
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }

    fun updateTasks(tasks: List<Task>) {
        viewModelScope.launch {
            updateTasksUseCase(tasks)
        }
    }

}