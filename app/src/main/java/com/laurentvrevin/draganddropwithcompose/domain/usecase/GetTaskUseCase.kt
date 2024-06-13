package com.laurentvrevin.draganddropwithcompose.domain.usecase

import com.laurentvrevin.draganddropwithcompose.domain.model.Task
import com.laurentvrevin.draganddropwithcompose.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTaskUseCase(private val repository: TaskRepository) {
    operator fun invoke(): Flow<List<Task>> = repository.getTasks()
}