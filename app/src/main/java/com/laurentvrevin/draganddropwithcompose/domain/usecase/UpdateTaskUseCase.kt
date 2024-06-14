package com.laurentvrevin.draganddropwithcompose.domain.usecase

import com.laurentvrevin.draganddropwithcompose.domain.model.Task
import com.laurentvrevin.draganddropwithcompose.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(tasks: List<Task>) {
        repository.updateTasks(tasks)
    }
}