package com.laurentvrevin.draganddropwithcompose.data.repository

import com.laurentvrevin.draganddropwithcompose.data.local.TaskDao
import com.laurentvrevin.draganddropwithcompose.domain.model.Task
import com.laurentvrevin.draganddropwithcompose.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val taskDao: TaskDao):TaskRepository {
    override fun getTasks(): Flow<List<Task>> = taskDao.getTasks()

    override suspend fun addTask(task: Task) = taskDao.addTask(task)

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}