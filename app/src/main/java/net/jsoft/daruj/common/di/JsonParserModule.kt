package net.jsoft.daruj.common.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.jsoft.daruj.common.misc.GsonParser
import net.jsoft.daruj.common.misc.JsonParser
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JsonParserModule {

    @Provides
    @Singleton
    fun provideJsonParser(): JsonParser {
        return GsonParser(Gson())
    }
}