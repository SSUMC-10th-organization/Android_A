package com.example.umc_10th.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface CategoryDAO {
    @Insert
    fun insertCategory(category: CategoryEntity)

    @Delete
    fun deleteCategory(category: CategoryEntity)

    @Update
    fun updateCategory(category: CategoryEntity)

    @Query("SELECT * from CategoryTable")
    fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT * from CategoryTable where id = :categoryId")
    fun getCategoriesByCategoryId(categoryId: Int): List<CategoryEntity>

}