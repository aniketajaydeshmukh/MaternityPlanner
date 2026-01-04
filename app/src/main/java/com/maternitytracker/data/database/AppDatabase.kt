package com.maternitytracker.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import com.maternitytracker.data.dao.LabelDao
import com.maternitytracker.data.dao.ShoppingItemDao
import com.maternitytracker.data.entities.Label
import com.maternitytracker.data.entities.ShoppingItem

@Database(
    entities = [ShoppingItem::class, Label::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao
    abstract fun labelDao(): LabelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Database Migration from v1 to v2
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE shopping_items ADD COLUMN actualPrice REAL")
                database.execSQL("ALTER TABLE shopping_items ADD COLUMN purchasedAt INTEGER")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "maternity_tracker_database"
                )
                .addMigrations(MIGRATION_1_2)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}