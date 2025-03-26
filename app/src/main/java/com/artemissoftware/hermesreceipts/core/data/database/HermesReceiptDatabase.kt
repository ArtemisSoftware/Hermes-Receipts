package com.artemissoftware.hermesreceipts.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artemissoftware.hermesreceipts.core.data.database.dao.ReceiptDao
import com.artemissoftware.hermesreceipts.core.data.database.entity.ReceiptEntity

@Database(
    entities = [
        ReceiptEntity::class
    ],
    version = 1,
)
abstract class HermesReceiptDatabase : RoomDatabase() {
    abstract val receiptDao: ReceiptDao
}