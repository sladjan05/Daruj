package net.jsoft.daruj.common.misc

interface JsonParser {
    fun <T> fromJson(json: String, `class`: Class<T>): T
    fun toJson(data: Any): String
}

inline fun <reified T> JsonParser.fromJson(json: String) = fromJson(json, T::class.java)