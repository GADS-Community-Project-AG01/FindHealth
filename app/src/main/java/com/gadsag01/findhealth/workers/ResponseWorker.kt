package com.gadsag01.findhealth.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.google.maps.model.PlacesSearchResponse
import com.google.maps.model.PlacesSearchResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ResponseWorker @AssistedInject constructor (
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val client: NearbyHospitalsSearchClient
): Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val responses: List<PlacesSearchResponse> = inputData.keyValueMap["LOCATION"] as List<PlacesSearchResponse> ?: return Result.failure()
        val result = responses.map {
            it.results
        }.fold(listOf<PlacesSearchResult>()) { initial, current ->
            initial.plus(current)
        }.map {
            client.requestHospitalsNearby(it.placeId)
        }
        val data = Data.Builder()
            .putAll(mutableMapOf("HOSPITALS" to result) as Map<String, Any>).build()
        return Result.success(data)
    }
}
