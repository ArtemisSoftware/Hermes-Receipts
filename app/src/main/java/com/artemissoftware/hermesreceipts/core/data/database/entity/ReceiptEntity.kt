package com.artemissoftware.hermesreceipts.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipt")
data class ReceiptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val currency: String? = null,
    val total: Double? = null,
    val store: String? = null,
    val date: Long? = null,
)
