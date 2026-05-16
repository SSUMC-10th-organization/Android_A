package com.example.umc_10th.ui.compose

import androidx.annotation.DrawableRes
import com.example.umc_10th.R

sealed class ScreenRoute(
    val route: String,
    val label: String,
    @DrawableRes val iconRes: Int
) {
    object Home : ScreenRoute("home", "홈", R.drawable.ic_menu_home)
    object Purchase : ScreenRoute("purchase", "구매하기", R.drawable.ic_menu_list)
    object Wishlist : ScreenRoute("wishlist", "위시리스트", R.drawable.ic_menu_wish)
    object Cart : ScreenRoute("cart", "장바구니", R.drawable.ic_menu_bag)
    object Profile : ScreenRoute("profile", "프로필", R.drawable.ic_menu_user)
}