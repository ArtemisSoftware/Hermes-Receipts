package com.artemissoftware.hermesreceipts.navigation

import kotlinx.serialization.Serializable

object RootRoute {

    @Serializable
    data class Error(val message: String)
}