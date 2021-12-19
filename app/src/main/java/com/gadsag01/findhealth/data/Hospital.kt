package com.gadsag01.findhealth.data

import com.google.android.libraries.places.api.model.OpeningHours

data class Hospital(
    val name: String,
    val formattedAddress: String,
    val ID: String,
    val formatted_phone_number: String,
    val googleUrl: String,
    val openingHours: OpeningHours,
)
