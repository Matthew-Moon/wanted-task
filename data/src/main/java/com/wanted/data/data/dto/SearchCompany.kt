package com.wanted.data.data.dto

import androidx.core.net.toUri
import com.google.gson.annotations.SerializedName
import com.wanted.domain.model.CompanyModel
import com.wanted.domain.model.LogoImgModel
import com.wanted.domain.model.PagedCompanyModel
import com.wanted.domain.model.TitleImgModel

data class SearchCompany(
    val companies: List<Company>,
    val links: Links
)

data class Company(
    val description: String?,
    val id: Int,
    @SerializedName("logo_img")
    val logoImg: LogoImg?,
    val name: String,
    @SerializedName("title_img")
    val titleImg: TitleImg?,
    val url: String
)

data class Links(
    val next: Any?,
    val prev: Any?
)

data class LogoImg(
    val origin: String?,
    val thumb: String?
)

data class TitleImg(
    val origin: String?,
    val thumb: String?
)


fun SearchCompany.toDomain(): PagedCompanyModel {
    return PagedCompanyModel(
        companies = companies.map { it.toDomain() },
        nextOffset = extractOffset(links.next),
        prevOffset = extractOffset(links.prev)
    )
}

fun Company.toDomain(): CompanyModel = CompanyModel(
    description = description ?: "회사 소개가 곧 등록될 예정이에요.",
    id = id,
    logoImg = logoImg?.toDomain(),
    name = name,
    titleImg = titleImg?.toDomain(),
    url = url
)

fun LogoImg.toDomain(): LogoImgModel = LogoImgModel(
    origin = origin ?: "",
    thumb = thumb ?: ""
)

fun TitleImg.toDomain(): TitleImgModel = TitleImgModel(
    origin = origin ?: "",
    thumb = thumb ?: ""
)

private fun extractOffset(link: Any?): Int? {
    return (link as? String)?.let {
        it.toUri().getQueryParameter("offset")?.toInt()
    }
}