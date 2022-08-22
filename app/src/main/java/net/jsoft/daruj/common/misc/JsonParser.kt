package net.jsoft.daruj.common.misc

interface JsonParser {
    fun <T> fromJson(json: String, clazz: Class<T>): T
    fun toJson(data: Any): String
}