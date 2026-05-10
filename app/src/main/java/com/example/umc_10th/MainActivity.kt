package com.example.umc_10th

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.umc_10th.navigation.AppDestination
import com.example.umc_10th.navigation.BottomNavItem
import com.example.umc_10th.ui.screen.CartScreen
import com.example.umc_10th.ui.screen.HomeScreen
import com.example.umc_10th.ui.screen.ProfileScreen
import com.example.umc_10th.ui.screen.ShopScreen
import com.example.umc_10th.ui.screen.WishlistScreen
import com.example.umc_10th.ui.theme.Gray700
import com.example.umc_10th.ui.theme.UmcAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UmcAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Home,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            composable<AppDestination.Home> { HomeScreen() }
            composable<AppDestination.Shop> { ShopScreen() }
            composable<AppDestination.Wishlist> { WishlistScreen() }
            composable<AppDestination.Cart> {
                CartScreen(
                    onNavigateToShop = {
                        navController.navigate(AppDestination.Shop) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable<AppDestination.Profile> { ProfileScreen() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        BottomNavItem.entries.forEach { item ->
            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                NavigationBarItem(
                    selected = currentRoute == item.destination::class.qualifiedName,
                    onClick = {
                        navController.navigate(item.destination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(item.iconRes),
                            contentDescription = item.label
                        )
                    },
                    label = { Text(item.label) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = Color.Black,
                        unselectedIconColor = Gray700,
                        unselectedTextColor = Gray700,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
