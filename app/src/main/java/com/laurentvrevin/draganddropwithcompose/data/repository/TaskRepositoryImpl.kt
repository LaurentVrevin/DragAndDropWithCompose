package com.laurentvrevin.draganddropwithcompose.data.repository

import com.laurentvrevin.draganddropwithcompose.data.local.TaskDao
import com.laurentvrevin.draganddropwithcompose.domain.model.Task
import com.laurentvrevin.draganddropwithcompose.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao):TaskRepository {
    override fun getTasks(): Flow<List<Task>> = taskDao.getTasks()

    override suspend fun addTask(task: Task) = taskDao.addTask(task)
    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    override suspend fun updateTasksList(tasks: List<Task>) = taskDao.updateTasksList(tasks)

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}