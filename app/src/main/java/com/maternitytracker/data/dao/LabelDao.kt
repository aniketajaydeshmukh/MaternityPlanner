package com.maternitytracker.data.dao

import androidx.room.*
import com.maternitytracker.data.entities.Label
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {
    @Query("SELECT * FROM labels ORDER BY name ASC")
    fun getAllLabels(): Flow<List<Label>>

    @Query("SELECT * FROM labels WHERE id = :id")
    suspend fun getLabelById(id: Long): Label?

    @Query("SELECT * FROM labels WHERE name = :name")
    suspend fun getLabelByName(name: String): Label?

    @Insert
    suspend fun insertLabel(label: Label): Long

    @Update
    suspend fun updateLabel(label: Label)

    @Delete
    suspend fun deleteLabel(label: Label)

    @Query("DELETE FROM labels WHERE id = :id")
    suspend fun deleteLabelById(id: Long)

    @Query("SELECT COUNT(*) FROM labels")
    suspend fun getLabelCount(): Int
}