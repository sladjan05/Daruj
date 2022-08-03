package net.jsoft.daruj.common.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.seconds

fun ComposeContentTestRule.setScreenContent(
    screen: @Composable () -> Unit
) = setContent {
    DarujTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            screen()
        }
    }
}

fun ComposeContentTestRule.onUnmergedNodeWithTag(testTag: String) =
    onNodeWithTag(
        testTag = testTag,
        useUnmergedTree = true
    )

fun ComposeContentTestRule.onUnmergedDescendantNodeWithTag(
    parentTag: String,
    descendantTag: String
) = onAllNodesWithTag(
    testTag = descendantTag,
    useUnmergedTree = true
).filterToOne(hasAnyAncestor(hasTestTag(parentTag)))

fun ComposeContentTestRule.onUnmergedDescendantNodeWithText(
    parentTag: String,
    descendantText: String
) = onAllNodesWithText(
    text = descendantText,
    useUnmergedTree = true
).filterToOne(hasAnyAncestor(hasTestTag(parentTag)))

fun ComposeContentTestRule.onUnmergedDescendantNodeWithContentDescription(
    parentTag: String,
    descendantContentDescription: String
) = onAllNodesWithContentDescription(
    label = descendantContentDescription,
    useUnmergedTree = true
).filterToOne(hasAnyAncestor(hasTestTag(parentTag)))

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> emulateWork(
    coroutineContext: CoroutineContext = TestDispatchers.IO,
    block: suspend () -> T
) = withContext(coroutineContext) {
    delay(2.seconds)
    block()
}