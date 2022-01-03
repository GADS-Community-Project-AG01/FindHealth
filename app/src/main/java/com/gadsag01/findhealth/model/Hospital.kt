package com.gadsag01.findhealth.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hospital(
    @PrimaryKey val placeId: String,
    val name: String,
    val formattedAddress: String?,
    val geometry: String,
    val phoneNumber: String?,
    val openingHours: String?,
    val rating: Float?,
    val userRatingsTotal: Int?,
    val googleUrl: String?,
    val website: String?,
    val photoReferences: String?
    )
