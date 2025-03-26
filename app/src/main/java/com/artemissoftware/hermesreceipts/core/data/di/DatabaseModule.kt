package com.artemissoftware.hermesreceipts.core.data.di

import android.content.Context
import androidx.room.Room
import com.artemissoftware.hermesreceipts.core.data.database.HermesReceiptDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        HermesReceiptDatabase::class.java,
        "hermes_receipt_db",
    )
        .build()
}