    package com.example.umc_10th.ui.main

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.appcompat.app.AppCompatActivity
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import com.example.umc_10th.R
    import com.example.umc_10th.data.local.SharedPreferenceManager
    import com.example.umc_10th.data.local.WishlistStorage
    import com.example.umc_10th.databinding.ActivityHomeBinding
    import com.example.umc_10th.ui.components.NikeBottomBar
    import com.example.umc_10th.ui.home.HomeFragment
    import com.example.umc_10th.ui.home.HomeScreen
    import com.example.umc_10th.ui.navigation.Screen
    import com.example.umc_10th.ui.profile.ProfileFragment
    import com.example.umc_10th.ui.purchase.PurchaseFragment
    import com.example.umc_10th.ui.purchase.PurchaseScreen
    import com.example.umc_10th.ui.shoppingcart.ShoppingCartScreen
    //import com.example.umc_10th.ui.shoppingcart.ShoppingcartFragment
    import com.example.umc_10th.ui.wishlist.WishlistFragment
    import dagger.hilt.android.AndroidEntryPoint
    import javax.inject.Inject

    @AndroidEntryPoint
    class MainActivity : ComponentActivity() {

        @Inject
        lateinit var hiltPrefManager: SharedPreferenceManager

        @Inject
        lateinit var hiltWishlistStorage: WishlistStorage

        // 다른 Fragment/Adapter에서 참조할 수 있도록 companion object 유지
        companion object {
            lateinit var wishlistStorage: WishlistStorage
            lateinit var prefManager: SharedPreferenceManager
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // 전역 인스턴스에 주입된 값 할당 (빌드 에러 해결 포인트)
            wishlistStorage = hiltWishlistStorage
            prefManager = hiltPrefManager

            // 기존 데이터 로드 로직 유지
            hiltWishlistStorage.loadFromDataStore()

            setContent {
                MaterialTheme {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = {
                            NikeBottomBar(navController = navController)
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            // 주석을 해제하여 모든 화면을 연결합니다.
                            composable(Screen.Home.route) { HomeScreen() }
                            composable(Screen.Purchase.route) { PurchaseScreen() }
                            composable(Screen.Wishlist.route) { WishlistScreen() }
                            composable(Screen.Cart.route) { ShoppingCartScreen(navController) }
                            composable(Screen.Profile.route) { ProfileScreen() }
                        }
                    }
                }
            }
        }
    }



    @Composable
    fun WishlistScreen() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("❤️ 위시리스트 화면 (성공!)")
        }
    }

    @Composable
    fun ProfileScreen() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("👤 프로필 화면 (성공!)")
        }
    }