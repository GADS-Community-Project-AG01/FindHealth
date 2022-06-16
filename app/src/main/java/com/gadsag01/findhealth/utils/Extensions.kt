package com.gadsag01.findhealth.utils

import com.google.maps.model.LatLng


class Extensions {
}

fun String.toLatLng() : LatLng {
    val res = this.split(",")
    return LatLng(res.first().toDouble(), res.last().toDouble())
}