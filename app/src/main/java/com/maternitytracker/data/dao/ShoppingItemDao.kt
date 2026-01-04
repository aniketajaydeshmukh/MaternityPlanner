package com.maternitytracker.data.dao

import androidx.room.*
import com.maternitytracker.data.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM shopping_items ORDER BY createdAt DESC")
    fun getAllItems(): Flow<List<ShoppingItem>>

    @Query("SELECT * FROM shopping_items WHERE id = :id")
    suspend fun getItemById(id: Long): ShoppingItem?

    @Query("SELECT * FROM shopping_items WHERE name LIKE '%' || :query || '%' AND isPurchased = 0")
    suspend fun searchUnpurchasedItems(query: String): List<ShoppingItem>

    @Query("SELECT * FROM shopping_items WHERE isPurchased = :isPurchased")
    fun getItemsByPurchaseStatus(isPurchased: Boolean): Flow<List<ShoppingItem>>

    @Insert
    suspend fun insertItem(item: ShoppingItem): Long

    @Update
    suspend fun updateItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)

    @Query("DELETE FROM shopping_items WHERE id = :id")
    suspend fun deleteItemById(id: Long)

    @Query("SELECT COUNT(*) FROM shopping_items")
    suspend fun getItemCount(): Int

    @Query("SELECT COUNT(*) FROM shopping_items WHERE isPurchased = 1")
    suspend fun getPurchasedItemCount(): Int

    @Query("SELECT SUM(estimatedPrice * quantity) FROM shopping_items")
    suspend fun getTotalEstimatedPrice(): Double?

    @Query("SELECT SUM(actualPrice * quantity) FROM shopping_items WHERE isPurchased = 1 AND actualPrice IS NOT NULL")
    suspend fun getTotalActualPrice(): Double?
}