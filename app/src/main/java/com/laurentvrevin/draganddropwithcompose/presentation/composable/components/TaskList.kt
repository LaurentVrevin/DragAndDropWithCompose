package com.laurentvrevin.draganddropwithcompose.presentation.composable.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.laurentvrevin.draganddropwithcompose.domain.model.Task
import com.laurentvrevin.draganddropwithcompose.presentation.viewmodel.TaskViewModel
import kotlin.math.roundToInt
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskList(tasks: List<Task>, viewModel: TaskViewModel) {
    var draggingItemIndex by remember { mutableStateOf<Int?>(null) }
    var draggingOffsetY by remember { mutableStateOf(0f) }
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(tasks) { index, task ->
            val elevation: Dp by animateDpAsState(if (index == draggingItemIndex) 16.dp else 4.dp)
            val modifier = if (index == draggingItemIndex) {
                Modifier
                    .zIndex(1f)
                    .offset(y = draggingOffsetY.dp)
            } else {
                Modifier.animateItemPlacement()
            }
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(8.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                draggingItemIndex = index
                            },
                            onDragEnd = {
                                draggingItemIndex = null
                                draggingOffsetY = 0f
                            },
                            onDragCancel = {
                                draggingItemIndex = null
                                draggingOffsetY = 0f
                            },
                            onDrag = { change, dragAmount ->
                                draggingOffsetY += dragAmount.y
                                val newIndex = (index + draggingOffsetY / 80).roundToInt().coerceIn(0, tasks.size - 1)
                                if (newIndex != index) {
                                    viewModel.moveTask(index, newIndex)
                                    draggingItemIndex = newIndex
                                    draggingOffsetY = 0f
                                }
                            }
                        )
                    }
            ) {
                TaskCard(task = task, elevation)
            }
        }
    }
}

