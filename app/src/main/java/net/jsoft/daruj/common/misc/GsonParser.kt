package net.jsoft.daruj.common.misc

import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GsonParser @Inject constructor(
    private val gson: Gson
) : JsonParser {
    override fun <T> fromJson(
        json: String,
        clazz: Class<T>
    ): T = gson.fromJson(json, clazz)

    override fun toJson(data: Any): String {
        return gson.toJson(data)
    }
}