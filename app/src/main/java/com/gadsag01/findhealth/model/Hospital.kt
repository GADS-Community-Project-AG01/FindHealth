package com.gadsag01.findhealth.model

import com.google.maps.model.Geometry
import com.google.maps.model.OpeningHours
import java.net.URL

data class Hospital(
    val placeId: String,
    val name: String,
    val formattedAddress: String?,
    val geometry: Geometry,
    val phoneNumber: String?,
    val openingHours: OpeningHours?,
    val rating: Float?,
    val userRatingsTotal: Int?,
    val googleUrl: URL,
    val website: URL?,
    val photoReferences: List<String>?
    )
