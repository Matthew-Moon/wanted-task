package com.wanted.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wanted.presentation.detail.CompanyDetailScreen
import com.wanted.presentation.list.CompanyListScreen
import com.wanted.presentation.navigation.ScreenRoute
import com.wanted.presentation.theme.WantedTaskTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            // 상태바 아이콘 색상 분기 처리
            LaunchedEffect(currentRoute) {
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
                    when (currentRoute) {
                        ScreenRoute.CompanyDetail.route -> false // 상태바 아이콘: 흰색
                        else -> true                             // 상태바 아이콘: 검정색
                    }
            }

            CompositionLocalProvider(LocalOverscrollFactory provides null) {
                WantedTaskTheme {
                    Surface(modifier = Modifier.Companion.fillMaxSize()) {
                        NavHost(
                            navController = navController,
                            startDestination = ScreenRoute.CompanyList.route
                        ) {
                            composable(ScreenRoute.CompanyList.route) {
                                CompanyListScreen(
                                    onCompanyClick = { companyId ->
                                        navController.navigate(ScreenRoute.CompanyDetail.createRoute(companyId))
                                    }
                                )
                            }

                            composable(
                                route = ScreenRoute.CompanyDetail.route,
                                arguments = listOf(navArgument("companyId") { type = NavType.Companion.IntType })
                            ) { backStackEntry ->
                                val companyId = backStackEntry.arguments?.getInt("companyId") ?: return@composable
                                CompanyDetailScreen(companyId = companyId, navController = navController)
                            }
                        }

                    }
                }

            }
        }
    }
}