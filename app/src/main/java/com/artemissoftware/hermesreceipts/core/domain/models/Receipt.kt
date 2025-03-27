package com.artemissoftware.hermesreceipts.core.domain.models

import java.time.LocalDate

data class Receipt(
    val id: Int = 0,
    val imagePath: String,
    val store: String? = null,
    val date: LocalDate? = null,
    val currency: String? = null,
    val total: Double? = null,
){
    fun totalPaid(): String {

        val currencyUsed = currency ?: ""

        return total?.let {
            it.toString() + currencyUsed
        } ?: run {
            ""
        }
    }
}
