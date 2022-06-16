package com.gadsag01.findhealth.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gadsag01.findhealth.data.AppDatabase
import com.gadsag01.findhealth.utils.LOCATION
import com.gadsag01.findhealth.utils.toLatLng
import com.google.maps.DistanceMatrixApiRequest
import com.google.maps.GeoApiContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DatabaseWorker @AssistedInject constructor (
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val DB: AppDatabase,
    val context: GeoApiContext
): CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val location = inputData.getString(LOCATION)
        try {
            DB.hospitalDao().getAllHospitalsSEQ().map {
                it.distance = DistanceMatrixApiRequest(context).origins(location?.toLatLng())
                    .destinations(it.geometry.toLatLng())
                    .await().rows.first().elements.first().distance.humanReadable
                return@map it
            }.forEach {
                DB.hospitalDao().updateHospital(it)
            }
        } catch(e: Exception) {return Result.failure()}
        return Result.success()
    }
}