package com.wanted.task.domain.model

import com.wanted.task.data.dto.Location
import com.wanted.task.data.dto.Viewport

data class CompanyInfoModel(
    val address: String?,
    val companyConfirm: Boolean,
    val description: String,
    val geoLocation: GeoLocationModel,
    val id: Int,
    val images: List<ImageModel>,
    val link: String,
    val logoUrl: LogoUrlModel,
    val name: String,
    val registrationNumber: String?,
    val url: String
)

data class GeoLocationModel(
    val location: Location,
    val locationType: String,
    val viewport: Viewport
)

data class ImageModel(
    val id: Int,
    val isTitle: Boolean,
    val origin: String,
    val thumb: String
)

data class LogoUrlModel(
    val origin: String,
    val thumb: String
)