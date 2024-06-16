package com.laurentvrevin.draganddropwithcompose.presentation.composable.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.laurentvrevin.draganddropwithcompose.presentation.viewmodel.TaskViewModel
import com.laurentvrevin.draganddropwithcompose.presentation.composable.components.TaskCard
import com.laurentvrevin.draganddropwithcompose.domain.draganddrop.rememberDragDropListState
import com.laurentvrevin.draganddropwithcompose.domain.draganddrop.handleDragAndDrop
import com.laurentvrevin.draganddropwithcompose.domain.util.updateTaskData


@Composable
fun TaskList(taskViewModel: TaskViewModel) {
    val tasks by taskViewModel.tasks.collectAsState(initial = emptyList())
    var data by remember { mutableStateOf(tasks) }

    val reorderableState = rememberDragDropListState(onMove = { from, to ->
        data = updateTaskData(taskViewModel, data, from, to)
    })

    LaunchedEffect(tasks) {
        data = tasks
    }

    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = reorderableState.lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .handleDragAndDrop(reorderableState, coroutineScope)
    ) {
        itemsIndexed(data) { index, task ->
            val isDragging = index == reorderableState.currentIndexOfDraggedItem
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .zIndex(if (isDragging) 1f else 0f)
                    .graphicsLayer {
                        if (isDragging) {
                            translationY = reorderableState.elementDisplacement ?: 0f
                        }
                    }
            ) {
                TaskCard(
                    task = task,
                    isDragging = isDragging, onDelete = { taskViewModel.deleteTask(task) },
                    onToggleComplete = {
                        val updatedTask = task.copy(isCompleted = !task.isCompleted)
                        taskViewModel.updateTask(updatedTask)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
