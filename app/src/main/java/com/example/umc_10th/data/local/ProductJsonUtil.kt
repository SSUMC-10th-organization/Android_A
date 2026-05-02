package com.example.umc_10th.data.local

import com.example.umc_10th.data.model.ProductData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ProductJsonUtil {

    private val gson = Gson()

    fun productListToJson(productList: List<ProductData>): String {
        return gson.toJson(productList)
    }

    fun jsonToProductList(jsonString: String): List<ProductData> {
        val type = object : TypeToken<List<ProductData>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}