package com.wanted.task.presentation.navigation

sealed class ScreenRoute(val route: String) {
    object CompanyList : ScreenRoute("company_list")
    object CompanyDetail : ScreenRoute("company_detail/{companyId}") {
        fun createRoute(companyId: Int) = "company_detail/$companyId"
    }
}