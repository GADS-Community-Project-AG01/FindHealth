package com.gadsag01.findhealth.data

import com.google.maps.model.Geometry
import com.google.maps.model.OpeningHours
import java.net.URL

data class HospitalBasic(val placeId: String,
                         val name: String,
                         val formattedAddress: String?,
                         val rating: Float?,
)

data class HospitalFull(
    val placedId: String,
    val name: String,
    val formattedAddress: String?,
    val geometry: Geometry,
    val phoneNumber: String?,
    val openingHours: OpeningHours?,
    val rating: Float?,
    val userRatingsTotal: Int,
    val googleUrl: URL,
    val website: URL?
)