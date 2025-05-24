package com.wanted.task.data.dto

import com.wanted.task.domain.model.CompanyModel
import com.wanted.task.domain.model.LogoImgModel
import com.wanted.task.domain.model.TitleImgModel
import kotlinx.serialization.SerialName

data class SearchCompany(
    val companies: List<Company>,
    val links: Links
)

data class Company(
    val description: String,
    val id: Int,
    @SerialName("logo_img")
    val logoImg: LogoImg,
    val name: String,
    @SerialName("title_img")
    val titleImg: TitleImg,
    val url: String
)

data class Links(
    val next: Any,
    val prev: Any
)

data class LogoImg(
    val origin: String,
    val thumb: String
)

data class TitleImg(
    val origin: String,
    val thumb: String
)


fun Company.toDomain(): CompanyModel = CompanyModel(
    description = description,
    id = id,
    logoImg = logoImg.toDomain(),
    name = name,
    titleImg = titleImg.toDomain(),
    url = url
)

fun LogoImg.toDomain(): LogoImgModel = LogoImgModel(origin, thumb)

fun TitleImg.toDomain(): TitleImgModel = TitleImgModel(origin, thumb)

fun SearchCompany.toDomain(): List<CompanyModel> = companies.map { it.toDomain() }
