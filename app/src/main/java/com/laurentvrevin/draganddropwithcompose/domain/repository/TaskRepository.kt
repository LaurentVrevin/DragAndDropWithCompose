package com.laurentvrevin.draganddropwithcompose.domain.repository

import com.laurentvrevin.draganddropwithcompose.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun updateTasksList(tasks: List<Task>)
    suspend fun deleteTask(task: Task)
}