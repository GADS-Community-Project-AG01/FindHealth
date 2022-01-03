package com.gadsag01.findhealth.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.data.AppDatabase
import com.gadsag01.findhealth.model.Hospital
import com.gadsag01.findhealth.utils.LOCATION
import com.google.maps.model.LatLng
import com.google.maps.model.PlacesSearchResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NearbyRequestWorker @AssistedInject constructor (
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val client: NearbyHospitalsSearchClient,
    private val DB: AppDatabase
): Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val location = inputData.getString(LOCATION)?.toLatLng() ?: return Result.failure()
        var result: List<Hospital> = listOf()
        val outputData: MutableMap<String, List<PlacesSearchResponse>> = mutableMapOf()
        val response: PlacesSearchResponse
        try {
            response = client.run(location)
//            result.add(response)
        } catch (e: Exception) {
            return Result.failure()
        }
//        while (response.nextPageToken != null) {
//            result.add(client.pagedRequest(location, response.nextPageToken))
//        }

        result = response.results.toList().map {
            client.requestHospitalsNearby(it.placeId)
        }

        DB.hospitalDao().insertHospitals(result)
        return Result.success()
//        outputData["RESPONSE"] = result
//        return Result.success(
//            Data.Builder()
//            .putAll(outputData as Map<String, Any>).build())
    }


    fun String.toLatLng() : LatLng {
        val res = this.split(",")
        return LatLng(res.first().toDouble(), res.last().toDouble())
    }
}