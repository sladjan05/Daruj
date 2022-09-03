package net.jsoft.daruj.common.misc

import com.google.gson.Gson

class GsonParser(
    private val gson: Gson
) : JsonParser {
    override fun <T> fromJson(
        json: String,
        `class`: Class<T>
    ): T = gson.fromJson(json, `class`)

    override fun toJson(data: Any): String {
        return gson.toJson(data)
    }
}