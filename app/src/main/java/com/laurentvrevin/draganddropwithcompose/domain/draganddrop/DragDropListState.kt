package com.laurentvrevin.draganddropwithcompose.domain.draganddrop

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.Job

// This composable function creates and remembers an instance of DragDropListState.
// It initializes the state with a LazyListState and a callback function to handle item moves.
@Composable
fun rememberDragDropListState(
    lazyListState: LazyListState = rememberLazyListState(), // The state of the lazy list.
    onMove: (Int, Int) -> Unit // Callback function to handle item moves.
): DragDropListState {
    return remember { DragDropListState(lazyListState = lazyListState, onMove = onMove) }
}

// Class that manages the state and logic for drag-and-drop functionality within a LazyList.
class DragDropListState(
    val lazyListState: LazyListState, // State of the lazy list.
    private val onMove: (Int, Int) -> Unit // Callback to handle item moves.
) {
    // Total distance the item has been dragged.
    private var draggedDistance by mutableFloatStateOf(0f)

    // Information about the initially dragged element.
    private var initiallyDraggedElement by mutableStateOf<LazyListItemInfo?>(null)

    // Index of the currently dragged item.
    var currentIndexOfDraggedItem by mutableStateOf<Int?>(null)

    // Pair of initial offsets (start and end) of the dragged element.
    private val initialOffsets: Pair<Int, Int>?
        get() = initiallyDraggedElement?.let {
            Pair(it.offset, it.offsetEnd)
        }

    // Calculated displacement of the dragged element based on the current drag distance.
    val elementDisplacement: Float?
        get() = currentIndexOfDraggedItem
            ?.let { lazyListState.getVisibleItemInfoFor(it) } // Get visible item info for the current dragged item.
            ?.let { item ->
                val initialOffset = initiallyDraggedElement?.offset ?: 0
                initialOffset.toFloat() + draggedDistance - item.offset // Calculate the displacement.
            }

    // Information about the currently visible and dragged element.
    private val currentElement: LazyListItemInfo?
        get() = currentIndexOfDraggedItem?.let { lazyListState.getVisibleItemInfoFor(it) }

    // Job to handle over-scroll during drag.
    private var overScrollJob by mutableStateOf<Job?>(null)

    // Method called when dragging starts.
    // It identifies the item being dragged and sets the initial state.
    fun onDragStart(offset: Offset) {
        lazyListState.layoutInfo.visibleItemsInfo
            .firstOrNull { item ->
                offset.y.toInt() in item.offset..(item.offset + item.size) // Check if the drag start position is within the item bounds.
            }?.also {
                currentIndexOfDraggedItem = it.index // Set the index of the dragged item.
                initiallyDraggedElement = it // Set the initial element being dragged.
            }
    }

    // Method called when dragging is interrupted (e.g., drag ends or is canceled).
    // It resets the state.
    fun onDragInterrupted() {
        draggedDistance = 0f
        currentIndexOfDraggedItem = null
        initiallyDraggedElement = null
        overScrollJob?.cancel() // Cancel any ongoing over-scroll job.
    }

    // Method called during dragging to update the dragged distance and handle item moves.
    fun onDrag(offset: Offset) {
        draggedDistance += offset.y // Update the total dragged distance.

        initialOffsets?.let { (topOffset, bottomOffset) ->
            val startOffset = topOffset + draggedDistance
            val endOffset = bottomOffset + draggedDistance

            currentElement?.let { hovered ->
                lazyListState.layoutInfo.visibleItemsInfo
                    .filterNot { item -> item.offsetEnd < startOffset || item.offset > endOffset || hovered.index == item.index }
                    .firstOrNull { item ->
                        val delta = startOffset - hovered.offset
                        when {
                            delta > 0 -> endOffset > item.offsetEnd // Check if the dragged item overlaps with another item.
                            else -> startOffset < item.offset
                        }
                    }?.also { item ->
                        currentIndexOfDraggedItem?.let { current ->
                            onMove(current, item.index) // Invoke the callback to move the item.
                        }
                        currentIndexOfDraggedItem = item.index // Update the index of the dragged item.
                    }
            }
        }
    }

    // Method to check and handle over-scroll during drag.
    // Returns the amount of over-scroll needed.
    fun checkForOverScroll(): Float {
        return initiallyDraggedElement?.let {
            val startOffset = it.offset + draggedDistance
            val endOffset = it.offsetEnd + draggedDistance

            when {
                draggedDistance > 0 -> (endOffset - lazyListState.layoutInfo.viewportEndOffset).takeIf { it > 0 } // Over-scroll down.
                draggedDistance < 0 -> (startOffset - lazyListState.layoutInfo.viewportStartOffset).takeIf { it < 0 } // Over-scroll up.
                else -> null
            } ?: 0f
        } ?: 0f
    }
}

// Extension function to get visible item info for a given index in the lazy list.
fun LazyListState.getVisibleItemInfoFor(absolute: Int): LazyListItemInfo? {
    return this.layoutInfo.visibleItemsInfo.getOrNull(absolute - this.layoutInfo.visibleItemsInfo.first().index)
}

// Extension property to get the end offset of a LazyListItemInfo.
val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size