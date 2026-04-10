package com.example.umc_10th.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 1. DataStore 선언 및 초기화
val Context.dataStore by preferencesDataStore(name = "user_prefs")
//user_prefs라는 이름의 로컬 저장소 파일을 생성

class SharedPreferenceManager(private val context: Context) {
    private val gson = Gson()

    companion object {
        // 2. 키(Key) 설정 (Companion Object)
        val KEY_HOME_PRODUCTS = stringPreferencesKey("home_products")
        val KEY_PURCHASE_PRODUCTS = stringPreferencesKey("purchase_products")
        val KEY_WISHLIST = stringPreferencesKey("wishlist_items")
        //저장된 데이터를 찾기 위한 이름표(Key)를 정의
        //stringPreferencesKey를 사용해 타입이 안전한(Type-safe) 키를 미리 만들어 둠
    }

    // 3. 데이터 저장 로직 (Serialization)
    suspend fun <T> saveObjectList(key: androidx.datastore.preferences.core.Preferences.Key<String>, list: List<T>) {
        //suspend : 이 함수는 비동기로 작동하므로 코루틴 안에서 실행되어야 함을 의미
        //(데이터를 파일에 쓰는 동안 UI가 멈추지 않게 함)

        val jsonString = gson.toJson(list)
        //코틀린 객체 리스트를 텍스트 형태인 JSON 문자열로 변환

        context.dataStore.edit { preferences ->
            //실제 파일의 내용을 수정(쓰기)하는 블록
            preferences[key] = jsonString
        }
    }

    // 4. 데이터 불러오기 로직 (Deserialization)
    fun <T> getObjectList(key: androidx.datastore.preferences.core.Preferences.Key<String>, typeToken: java.lang.reflect.Type): Flow<List<T>> {
        //Flow<List<T>> : 데이터가 바뀔 때마다 실시간으로 관찰할 수 있는 흐름(Stream)을 반환
        return context.dataStore.data.map { preferences ->
            //.map{} : 저장소에서 읽어온 원본 데이터를 가공
            val jsonString = preferences[key] ?: ""
            if (jsonString.isEmpty()) emptyList()
            //저장된 데이터가 없을 경우 에러 대신 빈 리스트를 반환하여 안전하게 처리
            else gson.fromJson(jsonString, typeToken)
            //저장되어 있던 JSON 문자열을 다시 우리가 사용할 수 있는 코틀린 객체 리스트로 복구
        }
    }
}