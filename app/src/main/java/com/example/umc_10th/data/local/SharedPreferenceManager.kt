package com.example.umc_10th.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext // 추가
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type
import javax.inject.Inject // 추가
import javax.inject.Singleton // 추가

val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton // 앱 전체에서 하나만 존재하도록 설정
class SharedPreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context // Hilt가 Context를 주입해줍니다.
) {
    private val gson = Gson()

    companion object {
        val KEY_HOME_PRODUCTS = stringPreferencesKey("home_products")
        val KEY_PURCHASE_PRODUCTS = stringPreferencesKey("purchase_products")
        val KEY_WISHLIST = stringPreferencesKey("wishlist_items")
        val KEY_USER_LIST = stringPreferencesKey("user_list")
    }

    // 데이터 저장
    suspend fun <T> saveObjectList(key: Preferences.Key<String>, list: List<T>) {
        val jsonString = gson.toJson(list)
        context.dataStore.edit { preferences ->
            preferences[key] = jsonString
        }
    }

    // 데이터 불러오기
    fun <T> getObjectList(key: Preferences.Key<String>, typeToken: Type): Flow<List<T>> {
        return context.dataStore.data.map { preferences ->
            val jsonString = preferences[key] ?: ""
            if (jsonString.isEmpty()) emptyList()
            else gson.fromJson(jsonString, typeToken)
        }
    }
}