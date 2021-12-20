package com.gadsag01.findhealth.api

import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.errors.ApiException
import com.google.maps.model.LatLng
import com.google.maps.model.PlaceType
import com.google.maps.model.PlacesSearchResponse
import com.google.maps.model.RankBy

class PlacesService {
}


class NearbySearchService (private val geoApiContext: GeoApiContext) {

    val location = FindCurrentPlaceRequest.builder(listOf(Place.Field.LAT_LNG)).build()

    var request : PlacesSearchResponse = PlacesSearchResponse()

    suspend fun run(location: LatLng, distance: Int = 5000 ) : PlacesSearchResponse {
        try {
            request = PlacesApi.nearbySearchQuery(geoApiContext, location)
                .radius(distance)
                .rankby(RankBy.PROMINENCE)
                .type(PlaceType.HOSPITAL)
                .await()
        } catch (e: ApiException) {

        } finally {
            return request
        }
    }
}