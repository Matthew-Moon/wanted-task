package com.wanted.data.data.dto

import com.google.gson.annotations.SerializedName
import com.wanted.domain.model.CompanyInfoModel
import com.wanted.domain.model.GeoLocationModel
import com.wanted.domain.model.ImageModel
import com.wanted.domain.model.LocationModel
import com.wanted.domain.model.LogoUrlModel
import com.wanted.domain.model.ViewportModel
import com.wanted.domain.model.ViewportPositionModel

data class CompanyDetail(
    @SerializedName("company")
    val companyInfo: CompanyInfo
)

data class CompanyInfo(
    val address: String?,
    @SerializedName("company_confirm")
    val companyConfirm: Boolean,
    val description: String,
    @SerializedName("geo_location")
    val geoLocation: GeoLocation?,
    val id: Int,
    val images: List<Image>,
    val link: String?,
    @SerializedName("logo_url")
    val logoUrl: LogoUrl,
    val name: String,
    @SerializedName("registration_number")
    val registrationNumber: String?,
    val url: String
)

data class GeoLocation(
    val location: Location,
    @SerializedName("locationType")
    val locationType: String,
    val viewport: Viewport
)

data class Image(
    val id: Int,
    @SerializedName("is_title")
    val isTitle: Boolean,
    val origin: String,
    val thumb: String
)

data class LogoUrl(
    val origin: String?,
    val thumb: String?
)

data class Location(
    val lat: Double,
    val lng: Double
)

data class Viewport(
    val northeast: Northeast,
    val southwest: Southwest
)

data class Northeast(
    val lat: Double,
    val lng: Double
)

data class Southwest(
    val lat: Double,
    val lng: Double
)


fun CompanyInfo.toDomain(): CompanyInfoModel {
    return CompanyInfoModel(
        address = address ?: "",
        companyConfirm = companyConfirm,
        description = description,
        geoLocation = geoLocation?.toDomain() ?: GeoLocationModel(
            location = LocationModel(0.0, 0.0),
            locationType = "",
            viewport = ViewportModel(
                northeast = ViewportPositionModel(0.0, 0.0),
                southwest = ViewportPositionModel(0.0, 0.0)
            )
        ),
        id = id,
        images = images.map { it.toDomain() },
        link = link ?: "",
        logoUrl = logoUrl.toDomain(),
        name = name,
        registrationNumber = registrationNumber,
        url = url
    )
}

fun GeoLocation.toDomain(): GeoLocationModel {
    return GeoLocationModel(
        location = location.toDomain(),
        locationType = locationType,
        viewport = viewport.toDomain(),
    )
}

fun Image.toDomain(): ImageModel {
    return ImageModel(
        id = id,
        isTitle = isTitle,
        origin = origin,
        thumb = thumb
    )
}

fun LogoUrl.toDomain(): LogoUrlModel {
    return LogoUrlModel(
        origin = origin ?: "",
        thumb = thumb ?: ""
    )
}

fun CompanyDetail.toDomain(): CompanyInfoModel {
    return companyInfo.toDomain()
}

fun Location.toDomain(): LocationModel {
    return LocationModel(
        lat = lat,
        lng = lng,
    )
}

fun Viewport.toDomain(): ViewportModel {
    return ViewportModel(
        northeast = northeast.toDomain(),
        southwest = southwest.toDomain(),
    )
}

fun Northeast.toDomain(): ViewportPositionModel {
    return ViewportPositionModel(
        lat = lat,
        lng = lng,
    )
}

fun Southwest.toDomain(): ViewportPositionModel {
    return ViewportPositionModel(
        lat = lat,
        lng = lng,
    )
}
