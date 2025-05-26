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
) {
    companion object {
        fun placeholder(): ImageModel {
            val url = "https://cdn.pixabay.com/photo/2019/07/14/16/27/pen-4337521_1280.jpg"
            return ImageModel(
                id = -1,
                isTitle = false,
                origin = url,
                thumb = url
            )
        }
    }
}

data class LogoUrlModel(
    val origin: String,
    val thumb: String
)