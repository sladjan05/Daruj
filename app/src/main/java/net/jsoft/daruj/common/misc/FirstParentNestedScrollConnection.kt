package net.jsoft.daruj.common.misc

import androidx.compose.foundation.ScrollState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import kotlin.math.min

class FirstParentNestedScrollConnection(
    private val scrollState: ScrollState
) : NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = -available.y

        val parentCurrent = scrollState.value
        val parentMax = scrollState.maxValue
        val parentRequires = parentMax - parentCurrent

        if ((parentRequires == 0) || ((parentCurrent == 0) && (delta < 0))) return Offset.Zero

        val consumed = min(delta, parentRequires.toFloat())
        scrollState.dispatchRawDelta(consumed)

        return Offset(0f, -consumed)
    }
}