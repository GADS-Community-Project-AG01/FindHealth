package com.gadsag01.findhealth.data

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Operation
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.model.Hospital
import com.gadsag01.findhealth.utils.LOCATION
import com.gadsag01.findhealth.workers.DatabaseWorker
import com.gadsag01.findhealth.workers.NearbyRequestWorker
import com.gadsag01.findhealth.workers.ResponseWorker
import com.google.maps.model.LatLng
import com.google.maps.model.PlacesSearchResponse
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class HospitalRepository(val context: Context, private val hospitalDao: HospitalDao, val client: NearbyHospitalsSearchClient) {

    fun getAllHospitalsNearby(location: LatLng) : List<Hospital>? {
        return client.run(location)?.toHospital(client)
    }

    @OptIn(ExperimentalTime::class)
    fun syncHospitals(location: LatLng): Operation {
        Log.d("timing", "1")
        val enqueue: Operation
        val time = measureTimedValue {
        val inputLocation = workDataOf(LOCATION to location.toString())
            Log.d("timing", "2")

            val nearbySearchWorkRequest = OneTimeWorkRequestBuilder<NearbyRequestWorker>()
            .setInputData(inputLocation)
            .addTag("nearbySearch")
            .build()
        val responseWorkRequest = OneTimeWorkRequestBuilder<ResponseWorker>()
            .build()
        val databaseWorkRequest = OneTimeWorkRequestBuilder<DatabaseWorker>()
            .build()

            Log.d("timing", "3")

            enqueue = WorkManager.getInstance(context).enqueue(nearbySearchWorkRequest)
//            enqueue = WorkManager.getInstance(context).beginWith(nearbySearchWorkRequest)
//            .then(responseWorkRequest).then(databaseWorkRequest).enqueue()
    }
        Log.d("timing", time.toString())
        return enqueue
    }

    suspend fun getHospitals() = hospitalDao.getAllHospitals()
}

fun PlacesSearchResponse.toHospital(client: NearbyHospitalsSearchClient) : List<Hospital> {
    return this.results.toList().map { client.requestHospitalsNearby(it.placeId) }
}

