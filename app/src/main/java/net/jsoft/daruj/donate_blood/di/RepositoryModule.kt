package net.jsoft.daruj.donate_blood.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.jsoft.daruj.common.misc.DispatcherProvider
import net.jsoft.daruj.donate_blood.data.repository.ReceiptRepositoryImpl
import net.jsoft.daruj.donate_blood.data.source.remote.FirebaseReceiptApi
import net.jsoft.daruj.donate_blood.domain.repository.ReceiptRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideReceiptRepository(
        receiptApi: FirebaseReceiptApi,
        dispatcherProvider: DispatcherProvider
    ): ReceiptRepository = ReceiptRepositoryImpl(
        receiptApi = receiptApi,
        dispatcherProvider = dispatcherProvider
    )
}