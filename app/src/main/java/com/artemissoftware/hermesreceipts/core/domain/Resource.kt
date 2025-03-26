package com.artemissoftware.hermesreceipts.core.domain

import com.artemissoftware.hermesreceipts.core.domain.error.Error

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Failure<T>(val error: Error) : Resource<T>

    fun onSuccess(block: (T) -> Unit): Resource<T> {
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (Error) -> Unit): Resource<T> {
        if (this is Failure) block(error)
        return this
    }
}