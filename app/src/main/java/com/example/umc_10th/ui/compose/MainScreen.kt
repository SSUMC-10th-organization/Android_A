package com.example.umc_10th.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()

    val products = remember {
        val likedIds = LikeStorage.getLikedProductIds(context)

        mutableStateListOf<ProductUiModel>().apply {
            addAll(
                getInitialProducts().map { product ->
                    product.copy(
                        isLiked = likedIds.contains(product.id)
                    )
                }
            )
        }
    }

    fun toggleLike(productId: Int) {
        val index = products.indexOfFirst { it.id == productId }

        if (index != -1) {
            val product = products[index]

            products[index] = product.copy(
                isLiked = !product.isLiked
            )

            val likedIds = products
                .filter { it.isLiked }
                .map { it.id }
                .toSet()

            LikeStorage.saveLikedProductIds(
                context = context,
                likedIds = likedIds
            )
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            MainBottomBar(
                currentRoute = currentRoute,
                onItemClick = { screen ->
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ScreenRoute.Home.route) {
                HomeScreen(
                    products = products,
                    onLikeClick = { productId ->
                        toggleLike(productId)
                    }
                )
            }

            composable(ScreenRoute.Purchase.route) {
                PurchaseScreen(
                    products = products,
                    onLikeClick = { productId ->
                        toggleLike(productId)
                    }
                )
            }

            composable(ScreenRoute.Wishlist.route) {
                WishlistScreen(
                    products = products.filter { it.isLiked },
                    onLikeClick = { productId ->
                        toggleLike(productId)
                    }
                )
            }

            composable(ScreenRoute.Cart.route) {
                CartScreen(
                    onClickOrder = {
                        navController.navigate(ScreenRoute.Purchase.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            composable(ScreenRoute.Profile.route) {
                ProfileScreen()
            }
        }
    }
}