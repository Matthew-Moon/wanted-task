package com.wanted.task.domain.model


data class CompanyModel(
    val description: String,
    val id: Int,
    val logoImg: LogoImgModel,
    val name: String,
    val titleImg: TitleImgModel,
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

