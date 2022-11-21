package net.jsoft.daruj.common.data.source.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import net.jsoft.daruj.common.data.datastore.LocalSettings
import java.io.InputStream
import java.io.OutputStream

object LocalSettingsSerializer : Serializer<LocalSettings> {
    override val defaultValue: LocalSettings
        get() = LocalSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): LocalSettings {
        try {
            return LocalSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: LocalSettings,
        output: OutputStream
    ) = t.writeTo(output)
}