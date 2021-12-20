package com.gadsag01.findhealth.data

import com.google.maps.model.Geometry
import com.google.maps.model.OpeningHours
import java.net.URL

data class HospitalBasic(
    val name: String,
    val formattedAddress: String?,
    val ID: String,
    val rating: Float?,
)
