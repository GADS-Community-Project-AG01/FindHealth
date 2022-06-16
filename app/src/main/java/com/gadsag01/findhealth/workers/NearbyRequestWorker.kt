package com.gadsag01.findhealth.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.data.AppDatabase
import com.gadsag01.findhealth.data.toHospital
import com.gadsag01.findhealth.model.Hospital
import com.gadsag01.findhealth.utils.LOCATION
import com.gadsag01.findhealth.utils.toLatLng
import com.google.maps.model.PlacesSearchResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@HiltWorker
class NearbyRequestWorker @AssistedInject constructor (
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val client: NearbyHospitalsSearchClient,
    private val DB: AppDatabase
): CoroutineWorker(appContext, workerParams) {
    val location = inputData.getString(LOCATION)?.toLatLng()
    private var response: PlacesSearchResponse = PlacesSearchResponse()
    private var result: List<Hospital> = listOf()

    override suspend fun doWork(): Result {
        try {
            response = location?.let { client.run(it) } ?: return Result.failure()
        } catch (e: Exception) {
            return Result.failure()
        }

        result = response.toHospital(client)
        savetoDB(result)
        runBlocking {

            getNextPage(response.nextPageToken)
        }
        return Result.success()

    }

    private suspend fun savetoDB(result: List<Hospital>) {
        DB.hospitalDao().insertHospitals(result)
        Log.d("nextpagetoken", "databasesave")

    }

    private suspend fun getNextPage(nextPage: String) {
        Log.d("nextpagetoken", nextPage)
        val result: PlacesSearchResponse
        try {
            delay(5000)
            if (location != null) {
                result = client.pagedRequest(location, nextPage)
                savetoDB(result.toHospital(client))
                Log.d("nextnextpagetoken", result.nextPageToken)

                if ( result.nextPageToken != null ) {
                    getNextPage(result.nextPageToken)
                }
            }
        } catch (e: Exception) {
            Log.d("nextPageError", e.toString())
        }
    }

}