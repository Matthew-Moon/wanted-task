package com.wanted.data.data.dto

import com.google.gson.annotations.SerializedName
import com.wanted.domain.model.CompanyAutoCompleteModel

data class SearchCompanyAutoComplete(
    val `data`: List<SearchCompanyInfo>
)

data class SearchCompanyInfo(
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("logo_image")
    val logoImage: String?,
    val name: String
)


fun SearchCompanyInfo.toDomain(): CompanyAutoCompleteModel {
    return CompanyAutoCompleteModel(
        companyId = companyId,
        logoImage = logoImage ?: "",
        name = name,
    )

}
