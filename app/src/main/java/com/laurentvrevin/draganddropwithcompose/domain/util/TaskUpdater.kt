package com.laurentvrevin.draganddropwithcompose.domain.util

import com.laurentvrevin.draganddropwithcompose.domain.model.Task
import com.laurentvrevin.draganddropwithcompose.presentation.viewmodel.TaskViewModel
fun updateTaskData(
    taskViewModel: TaskViewModel,
    data: List<Task>,
    from: Int,
    to: Int
): List<Task> {
    val updatedData = data.toMutableList().apply {
        add(to, removeAt(from))
    }
    taskViewModel.updateTasks(updatedData)
    return updatedData
}