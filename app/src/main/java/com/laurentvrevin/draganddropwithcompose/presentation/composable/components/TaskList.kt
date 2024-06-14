package com.laurentvrevin.draganddropwithcompose.presentation.composable.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorder
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskList(taskViewModel: TaskViewModel) {
    val tasks by taskViewModel.tasks.collectAsState(initial = emptyList())
    var data by remember { mutableStateOf(tasks) }

    val reorderableState = rememberReorderableLazyListState(onMove = { from, to ->
        data = data.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        taskViewModel.updateTasks(data)
    })

    LaunchedEffect(tasks) {
        data = tasks
    }

    LazyColumn(
        state = reorderableState.listState,
        modifier = Modifier
            .fillMaxSize()
            .reorderable(reorderableState)
            .detectReorderAfterLongPress(reorderableState)
    ) {
        itemsIndexed(data) { index, task ->
            ReorderableItem(reorderableState, key = task.id) { isDragging ->
                val elevation: Dp by animateDpAsState(if (isDragging) 16.dp else 4.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .zIndex(if (isDragging) 1f else 0f)
                        .shadow(elevation.value.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .detectReorderAfterLongPress(reorderableState) // Détection de la réorganisation sur le conteneur
                ) {
                    TaskCard(
                        task = task,
                        elevation = elevation,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}
