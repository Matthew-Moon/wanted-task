package com.wanted.domain.model


data class PagedCompanyModel(
    val companies: List<CompanyModel>,
    val nextOffset: Int?,
    val prevOffset: Int?
)

data class CompanyModel(
    val description: String,
    val id: Int,
    val logoImg: LogoImgModel?,
    val name: String,
    val titleImg: TitleImgModel?,
    val url: String
)

data class LogoImgModel(
    val origin: String,
    val thumb: String
)

data class TitleImgModel(
    val origin: String,
    val thumb: String
)

