package com.gadsag01.findhealth.data

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Operation
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.model.Hospital
import com.gadsag01.findhealth.utils.LOCATION
import com.gadsag01.findhealth.workers.DatabaseWorker
import com.gadsag01.findhealth.workers.NearbyRequestWorker
import com.google.maps.model.LatLng
import com.google.maps.model.PlacesSearchResponse

class HospitalRepository(val context: Context, private val hospitalDao: HospitalDao, val client: NearbyHospitalsSearchClient) {

    fun getAllHospitalsNearby(location: LatLng) : List<Hospital> {
        return client.run(location).toHospital(client)
    }

    fun syncHospitals(location: LatLng): Operation {
        val inputLocation = workDataOf(LOCATION to location.toString())

        val nearbySearchWorkRequest = OneTimeWorkRequestBuilder<NearbyRequestWorker>()
        .setInputData(inputLocation)
        .addTag("nearbySearch")
        .build()

        return WorkManager.getInstance(context).enqueue(nearbySearchWorkRequest)
    }

    suspend fun getHospitals() = hospitalDao.getAllHospitals()

    fun syncDistances(location: LatLng): Operation {
        val originLocation = workDataOf(LOCATION to location.toString())

        val databaseWorkRequest = OneTimeWorkRequestBuilder<DatabaseWorker>()
            .setInputData(originLocation)
            .addTag("distancework")
            .build()

        return WorkManager.getInstance(context).enqueue(databaseWorkRequest)
    }
}

fun PlacesSearchResponse.toHospital(client: NearbyHospitalsSearchClient) : List<Hospital> {
    return this.results.toList().map { client.requestHospitalsNearby(it.placeId) }
}

