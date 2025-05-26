package com.wanted.task.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wanted.task.presentation.detail.CompanyDetailScreen
import com.wanted.task.presentation.list.CompanyListScreen
import com.wanted.task.presentation.navigation.ScreenRoute
import com.wanted.task.presentation.theme.WantedTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            CompositionLocalProvider(LocalOverscrollFactory provides null) {
                WantedTaskTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
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
                                arguments = listOf(navArgument("companyId") { type = NavType.IntType })
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