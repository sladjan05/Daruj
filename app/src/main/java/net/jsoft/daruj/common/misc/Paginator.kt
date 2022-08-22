package net.jsoft.daruj.common.misc

interface Paginator<T> {
    suspend fun getNextPage(): List<T>
    suspend fun reset()
}