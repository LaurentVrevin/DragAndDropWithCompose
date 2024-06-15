package com.laurentvrevin.draganddropwithcompose.domain.draganddrop

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Modifier.handleDragAndDrop(
    reorderableState: DragDropListState,
    coroutineScope: CoroutineScope
): Modifier = this.pointerInput(Unit) {
    detectDragGesturesAfterLongPress(
        onDrag = { change, offset ->
            change.consume()
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