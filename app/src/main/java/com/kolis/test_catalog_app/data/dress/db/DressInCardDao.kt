package com.kolis.test_catalog_app.data.dress.db

import androidx.room.*

@Dao
interface DressInCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItemInCard(dressEntity: DressInCartEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItemsInCard(dressEntities: List<DressInCartEntity>): List<Long>

    @Delete
    fun deleteItemFromCard(dressEntity: DressInCartEntity)

    @Query(value = "DELETE FROM ${DressInCartEntity.TABLE_NAME}")
    fun deleteAllItemsFromCard()

}