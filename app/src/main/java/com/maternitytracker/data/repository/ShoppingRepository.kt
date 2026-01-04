package com.maternitytracker.data.repository

import com.maternitytracker.data.dao.LabelDao
import com.maternitytracker.data.dao.ShoppingItemDao
import com.maternitytracker.data.entities.Label
import com.maternitytracker.data.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow
import java.util.Date

class ShoppingRepository(
    private val shoppingItemDao: ShoppingItemDao,
    private val labelDao: LabelDao
) {
    // Shopping Item operations
    fun getAllItems(): Flow<List<ShoppingItem>> = shoppingItemDao.getAllItems()
    
    suspend fun getItemById(id: Long): ShoppingItem? = shoppingItemDao.getItemById(id)
    
    suspend fun searchUnpurchasedItems(query: String): List<ShoppingItem> = 
        shoppingItemDao.searchUnpurchasedItems(query)
    
    fun getItemsByPurchaseStatus(isPurchased: Boolean): Flow<List<ShoppingItem>> = 
        shoppingItemDao.getItemsByPurchaseStatus(isPurchased)
    
    suspend fun insertItem(item: ShoppingItem): Long = shoppingItemDao.insertItem(item)
    
    suspend fun updateItem(item: ShoppingItem) = shoppingItemDao.updateItem(item)
    
    suspend fun deleteItem(item: ShoppingItem) = shoppingItemDao.deleteItem(item)
    
    suspend fun deleteItemById(id: Long) = shoppingItemDao.deleteItemById(id)
    
    suspend fun markItemAsPurchased(id: Long, actualPrice: Double) {
        val item = getItemById(id)
        item?.let {
            updateItem(it.copy(
                isPurchased = true,
                actualPrice = actualPrice,
                purchasedAt = Date()
            ))
        }
    }
    
    suspend fun markItemAsUnpurchased(id: Long) {
        val item = getItemById(id)
        item?.let {
            updateItem(it.copy(
                isPurchased = false,
                actualPrice = null,
                purchasedAt = null
            ))
        }
    }
    
    // Budget calculations
    suspend fun getTotalEstimatedPrice(): Double = shoppingItemDao.getTotalEstimatedPrice() ?: 0.0
    suspend fun getTotalActualPrice(): Double = shoppingItemDao.getTotalActualPrice() ?: 0.0
    suspend fun getItemCount(): Int = shoppingItemDao.getItemCount()
    suspend fun getPurchasedItemCount(): Int = shoppingItemDao.getPurchasedItemCount()
    
    // Label operations
    fun getAllLabels(): Flow<List<Label>> = labelDao.getAllLabels()
    
    suspend fun getLabelById(id: Long): Label? = labelDao.getLabelById(id)
    
    suspend fun getLabelByName(name: String): Label? = labelDao.getLabelByName(name)
    
    suspend fun insertLabel(label: Label): Long = labelDao.insertLabel(label)
    
    suspend fun updateLabel(label: Label) = labelDao.updateLabel(label)
    
    suspend fun deleteLabel(label: Label) = labelDao.deleteLabel(label)
    
    suspend fun deleteLabelById(id: Long) = labelDao.deleteLabelById(id)
}