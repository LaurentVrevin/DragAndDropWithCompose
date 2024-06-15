package com.laurentvrevin.draganddropwithcompose.presentation.composable.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.laurentvrevin.draganddropwithcompose.domain.model.Task
import com.laurentvrevin.draganddropwithcompose.presentation.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskList(taskViewModel: TaskViewModel) {
    val tasks by taskViewModel.tasks.collectAsState(initial = emptyList())
    var data by remember { mutableStateOf(tasks) }

    val reorderableState = rememberDragDropListState(onMove = { from, to ->
        data = data.toMutableList().apply {
            add(to, removeAt(from))
        }
        taskViewModel.updateTasks(data)
    })

    LaunchedEffect(tasks) {
        data = tasks
    }

    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = reorderableState.lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consumeAllChanges()
                        reorderableState.onDrag(offset)
                        val overScrollAmount = reorderableState.checkForOverScroll()
                        if (overScrollAmount != 0f) {
                            coroutineScope.launch {
                                reorderableState.lazyListState.scrollBy(overScrollAmount)
                            }
                        }
                    },
                    onDragStart = { offset -> reorderableState.onDragStart(offset) },
                    onDragEnd = { reorderableState.onDragInterrupted() },
                    onDragCancel = { reorderableState.onDragInterrupted() }
                )
            }
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
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                TaskCard(
                    task = task,
                    isDragging = isDragging,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}
