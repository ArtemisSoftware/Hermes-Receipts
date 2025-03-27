package com.artemissoftware.hermesreceipts.feature.receipts.data.di

import android.content.Context
import com.artemissoftware.hermesreceipts.core.data.database.HermesReceiptDatabase
import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.source.OcrApiSource
import com.artemissoftware.hermesreceipts.feature.receipts.data.repository.ReceiptsRepositoryImpl
import com.artemissoftware.hermesreceipts.feature.receipts.domain.repository.ReceiptsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideReceiptsRepository(@ApplicationContext context: Context, ocrApiSource: OcrApiSource, database: HermesReceiptDatabase): ReceiptsRepository {
        return ReceiptsRepositoryImpl(context = context, ocrApiSource = ocrApiSource, receiptDao = database.receiptDao)
    }
}