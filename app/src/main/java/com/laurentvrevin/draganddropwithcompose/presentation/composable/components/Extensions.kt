package com.laurentvrevin.draganddropwithcompose.presentation.composable.components

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.getVisibleItemInfoFor(absolute: Int): LazyListItemInfo? {
    return this.layoutInfo.visibleItemsInfo.getOrNull(absolute - this.layoutInfo.visibleItemsInfo.first().index)
}

val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size
