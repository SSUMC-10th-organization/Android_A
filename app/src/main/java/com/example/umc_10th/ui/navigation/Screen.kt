package com.example.umc_10th.ui.navigation

import com.example.umc_10th.R

sealed class Screen(val route: String, val title: String, val icon: Int) {
    object Home : Screen("home", "홈", R.drawable.housesimple_ic)
    object Purchase : Screen("purchase", "구매하기", R.drawable.listmagnifyingglass_ic)
    object Wishlist : Screen("wishlist", "위시리스트", R.drawable.heartstraight_ic)
    object Cart : Screen("cart", "장바구니", R.drawable.bagsimple_ic)
    object Profile : Screen("profile", "프로필", R.drawable.user_ic)
}