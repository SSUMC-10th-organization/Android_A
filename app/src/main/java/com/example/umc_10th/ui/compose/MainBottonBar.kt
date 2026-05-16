package com.example.umc_10th.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun MainBottomBar(
    currentRoute: String?,
    onItemClick: (ScreenRoute) -> Unit
) {
    val items = listOf(
        ScreenRoute.Home,
        ScreenRoute.Purchase,
        ScreenRoute.Wishlist,
        ScreenRoute.Cart,
        ScreenRoute.Profile
    )

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { onItemClick(screen) },
                icon = {
                    Image(
                        painter = painterResource(id = screen.iconRes),
                        contentDescription = screen.label
                    )
                },
                label = {
                    Text(text = screen.label)
                }
            )
        }
    }
}