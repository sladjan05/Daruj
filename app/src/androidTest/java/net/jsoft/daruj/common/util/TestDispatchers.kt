package net.jsoft.daruj.common.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
object TestDispatchers {
    val IO = StandardTestDispatcher()
    val Default = StandardTestDispatcher()
    val Main = StandardTestDispatcher()
    val Unconfined = StandardTestDispatcher()
}