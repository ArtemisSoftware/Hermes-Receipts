package com.artemissoftware.hermesreceipts.core.data.remote

internal object HandleApi {
    suspend fun <T> safeApiCall(callFunction: suspend () -> T): T {
        return try {
            callFunction.invoke()
        } catch (ex: Exception) {
            throw ex
        }
    }
}