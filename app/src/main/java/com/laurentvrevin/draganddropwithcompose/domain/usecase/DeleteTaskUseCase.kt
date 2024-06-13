package com.laurentvrevin.draganddropwithcompose.domain.usecase

import com.laurentvrevin.draganddropwithcompose.domain.model.Task
import com.laurentvrevin.draganddropwithcompose.domain.repository.TaskRepository

class DeleteTaskUseCase(private val repository:TaskRepository) {
    suspend operator fun invoke(task:Task) = repository.deleteTask(task)
}