package net.jsoft.daruj.common.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme

fun AndroidComposeTestRule<*, *>.setScreenContent(
    screen: @Composable () -> Unit
) = setContent {
    DarujTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            screen()
        }
    }
}

fun AndroidComposeTestRule<*, *>.onUnmergedNodeWithTag(testTag: String) =
    onNodeWithTag(
        testTag = testTag,
        useUnmergedTree = true
    )

fun AndroidComposeTestRule<*, *>.onUnmergedDescendantNodeWithTag(
    parentTag: String,
    descendantTag: String
) = onAllNodesWithTag(
    testTag = descendantTag,
    useUnmergedTree = true
).filterToOne(hasAnyAncestor(hasTestTag(parentTag)))

fun AndroidComposeTestRule<*, *>.onUnmergedDescendantNodeWithText(
    parentTag: String,
    descendantText: String
) = onAllNodesWithText(
    text = descendantText,
    useUnmergedTree = true
).filterToOne(hasAnyAncestor(hasTestTag(parentTag)))