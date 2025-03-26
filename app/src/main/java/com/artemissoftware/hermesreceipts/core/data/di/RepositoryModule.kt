package com.artemissoftware.hermesreceipts.core.data.di

import android.content.Context
import com.artemissoftware.hermesreceipts.core.data.repository.ImageRepositoryImpl
import com.artemissoftware.hermesreceipts.core.domain.repository.ImageRepository
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
    fun provideImageRepository(@ApplicationContext context: Context,): ImageRepository {
        return ImageRepositoryImpl(context = context)
    }
}