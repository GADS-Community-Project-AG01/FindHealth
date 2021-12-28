package com.gadsag01.findhealth.api

import android.util.Log
import com.gadsag01.findhealth.model.Hospital
import com.google.maps.GeoApiContext
import com.google.maps.ImageResult
import com.google.maps.NearbySearchRequest
import com.google.maps.PhotoRequest
import com.google.maps.PlacesApi
import com.google.maps.errors.ApiException
import com.google.maps.model.LatLng
import com.google.maps.model.PlaceDetails
import com.google.maps.model.PlaceType
import com.google.maps.model.PlacesSearchResponse
import com.google.maps.model.RankBy

class PlacesService {
}

class NearbyHospitalsSearchClient (private val geoApiContext: GeoApiContext) {

//    val location = FindCurrentPlaceRequest.builder(listOf(Place.Field.LAT_LNG)).build()

    var request : PlacesSearchResponse = PlacesSearchResponse()

    fun getPhoto(photoReference : String) {

        val photoRequest : ImageResult? = PhotoRequest(geoApiContext).apply {
            photoReference(photoReference)
        } .await()

        //todo if coil can load bytearray data, create an extension function on ImageView that loads the images
    }

    fun run(location: LatLng, distance: Int = 5000) : PlacesSearchResponse? {
        return try {
            request = NearbySearchRequest(geoApiContext).apply {
                location(location)
                radius(distance)
                rankby(RankBy.PROMINENCE)
                type(PlaceType.HOSPITAL)
            } .await()
            request
        } catch (e: ApiException) {
            Log.e("PlacesService", e.localizedMessage ?: "An unexpected error occurred")
            null
        }
    }

    fun requestHospitalsNearby(id: String) : Hospital {
        val placeDetails: PlaceDetails = PlacesApi.placeDetails(geoApiContext, id).await()
        return Hospital(
            placeDetails.placeId,
            placeDetails.name,
            placeDetails.formattedAddress,
            placeDetails.geometry,
            placeDetails.internationalPhoneNumber,
            placeDetails.openingHours,
            placeDetails.rating,
            placeDetails.userRatingsTotal,
            placeDetails.url,
            placeDetails.website,
            photoReferences = placeDetails.photos?.toList()?.map {
                it.photoReference
            }
        )
    }
}