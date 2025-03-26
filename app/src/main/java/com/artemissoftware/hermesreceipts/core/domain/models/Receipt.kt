package com.artemissoftware.hermesreceipts.core.domain.models

import java.time.LocalDate

data class Receipt(
    val imagePath: String,
    val store: String? = null,
    val date: LocalDate? = null,
    val currency: String? = null,
    val total: Double? = null,
)
