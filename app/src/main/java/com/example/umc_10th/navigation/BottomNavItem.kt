package com.example.umc_10th.navigation

import com.example.umc_10th.R

enum class BottomNavItem(
    val label: String,
    val iconRes: Int,
    val destination: AppDestination
) {
    HOME("홈", R.drawable.ic_home, AppDestination.Home),
    SHOP("구매하기", R.drawable.ic_shop, AppDestination.Shop),
    WISHLIST("위시리스트", R.drawable.ic_wishlist, AppDestination.Wishlist),
    CART("장바구니", R.drawable.ic_cart, AppDestination.Cart),
    PROFILE("프로필", R.drawable.ic_profile, AppDestination.Profile)
}
