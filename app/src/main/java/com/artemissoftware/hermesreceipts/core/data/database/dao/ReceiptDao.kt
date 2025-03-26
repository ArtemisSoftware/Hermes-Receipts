package com.artemissoftware.hermesreceipts.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artemissoftware.hermesreceipts.core.data.database.entity.ReceiptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {

    @Query("DELETE FROM receipt WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM receipt ORDER BY date")
    fun getAll(): Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM receipt  WHERE id = :id")
    suspend fun get(id: Int): ReceiptEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(receiptEntity: ReceiptEntity)
}