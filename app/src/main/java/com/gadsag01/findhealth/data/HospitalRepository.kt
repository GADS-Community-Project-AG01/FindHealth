package com.gadsag01.findhealth.data

import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.model.Hospital
import com.google.maps.model.LatLng
import com.google.maps.model.PlacesSearchResponse

class HospitalRepository(val client: NearbyHospitalsSearchClient) {

    fun getAllHospitalsNearby(location: LatLng) : List<Hospital>? {
        return client.run(location)?.toHospital(client)
    }

}

fun PlacesSearchResponse.toHospital(client: NearbyHospitalsSearchClient) : List<Hospital> {
    return this.results.toList().map { client.requestHospitalsNearby(it.placeId) }
}