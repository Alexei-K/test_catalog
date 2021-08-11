package com.kolis.test_catalog_app.data.dress.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DressInCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemInCard(dressEntity: DressInCartEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemsInCard(dressEntities: List<DressInCartEntity>): List<Long>

    @Delete
    suspend fun removeFromCart(dressEntity: DressInCartEntity)

    @Query(value = "DELETE FROM ${DressInCartEntity.TABLE_NAME}")
    fun deleteAllItemsFromCard()

    @Query(value = "SELECT COUNT(*) FROM ${DressInCartEntity.TABLE_NAME}")
    fun countDressInCart(): LiveData<Int>

    @Query(value = "SELECT * FROM ${DressInCartEntity.TABLE_NAME}")
    fun dressesInCart(): LiveData<List<DressInCartEntity>>
}