package com.maternitytracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) 
    val id: Long = 0,
    val name: String,
    val quantity: Int,
    val estimatedPrice: Double,
    val actualPrice: Double? = null,  // NEW in v2
    val labels: String,  // Comma-separated
    val isPurchased: Boolean = false,
    val purchasedAt: Date? = null,    // NEW in v2
    val createdAt: Date = Date()
)