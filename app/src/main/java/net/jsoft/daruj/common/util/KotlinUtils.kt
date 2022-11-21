package net.jsoft.daruj.common.util

/**
 * If [this] is true, value of the [block] is returned.
 * If [this] is false, null is returned.
 */
inline fun <T> Boolean.ifTrue(block: () -> T): T? = if (this) block() else null

/**
 * If [this] is true, null is returned.
 * If [this] is false, value of the [block] is returned.
 */
inline fun <T> Boolean.ifFalse(block: () -> T): T? = if (this) null else block()