package com.example.umc_10th.ui.compose

import android.content.Context

object LikeStorage {
    private const val PREF_NAME = "like_storage"
    private const val KEY_LIKED_PRODUCT_IDS = "liked_product_ids"

    fun getLikedProductIds(context: Context): Set<Int> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        return prefs.getStringSet(KEY_LIKED_PRODUCT_IDS, emptySet())
            ?.mapNotNull { it.toIntOrNull() }
            ?.toSet()
            ?: emptySet()
    }

    fun saveLikedProductIds(context: Context, likedIds: Set<Int>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        prefs.edit()
            .putStringSet(
                KEY_LIKED_PRODUCT_IDS,
                likedIds.map { it.toString() }.toSet()
            )
            .apply()
    }
}