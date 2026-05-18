package com.example.umc_10th.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class, CategoryEntity::class], version = 1, exportSchema= true)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun ProductDao(): ProductDAO
    abstract fun CategoryDao(): CategoryDAO

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "nike_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}