package com.gadsag01.findhealth.api

import android.util.Log
import com.gadsag01.findhealth.model.HospitalFull
import com.google.maps.GeoApiContext
import com.google.maps.NearbySearchRequest
import com.google.maps.PlacesApi
import com.google.maps.errors.ApiException
import com.google.maps.model.*

class PlacesService {
}


class NearbyHospitalsSearchClient (private val geoApiContext: GeoApiContext) {

//    val location = FindCurrentPlaceRequest.builder(listOf(Place.Field.LAT_LNG)).build()

    var request : PlacesSearchResponse = PlacesSearchResponse()

    fun run(location: LatLng, distance: Int = 5000) : PlacesSearchResponse {
        try {
            request = NearbySearchRequest(geoApiContext).apply {
                location(location)
                radius(distance)
                rankby(RankBy.PROMINENCE)
                type(PlaceType.HOSPITAL)
            } .await()
        } catch (e: ApiException) {
// todo
        } finally {
            Log.d("check value", request.toString())
            return request
        }
    }

    fun returnHospitalFullDetails(id: String) : HospitalFull {
        val placeDetails: PlaceDetails = PlacesApi.placeDetails(geoApiContext, id).await()
        return HospitalFull(
            placeDetails.placeId,
            placeDetails.name,
            placeDetails.formattedAddress,
            placeDetails.geometry,
            placeDetails.internationalPhoneNumber,
            placeDetails.openingHours,
            placeDetails.rating,
            placeDetails.userRatingsTotal,
            placeDetails.url,
            placeDetails.website
        )
    }
}