package com.example.umc_10th.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore 인스턴스 (파일 이름: "user_prefs")
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SharedPreferenceManager(private val context: Context) {
    private val gson = Gson()

    companion object {
        // 데이터를 찾기 위한 열쇠(Key)들
        val KEY_HOME_PRODUCTS = stringPreferencesKey("home_products")
        val KEY_PURCHASE_PRODUCTS = stringPreferencesKey("purchase_products")
        val KEY_WISHLIST = stringPreferencesKey("wishlist_items")
    }

    // --- [저장 로직] 객체 리스트를 JSON 문자열로 변환해 저장 ---
    suspend fun <T> saveObjectList(key: androidx.datastore.preferences.core.Preferences.Key<String>, list: List<T>) {
        val jsonString = gson.toJson(list)
        context.dataStore.edit { preferences ->
            preferences[key] = jsonString
        }
    }

    // --- [불러오기 로직] JSON 문자열을 다시 객체 리스트로 변환 ---
    fun <T> getObjectList(key: androidx.datastore.preferences.core.Preferences.Key<String>, typeToken: java.lang.reflect.Type): Flow<List<T>> {
        return context.dataStore.data.map { preferences ->
            val jsonString = preferences[key] ?: ""
            if (jsonString.isEmpty()) emptyList()
            else gson.fromJson(jsonString, typeToken)
        }
    }
}