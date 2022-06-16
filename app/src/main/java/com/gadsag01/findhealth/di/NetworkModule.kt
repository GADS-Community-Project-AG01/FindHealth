package com.gadsag01.findhealth.di

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.google.maps.GeoApiContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesGeoApiContext(@ApplicationContext context: Context): GeoApiContext {
        // retreive api key stored in local.properties
        val ai: ApplicationInfo = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )
        val apiKey = ai.metaData["API_KEY"].toString()
        Log.d("api", apiKey)

        return GeoApiContext.Builder()
            .apiKey(apiKey)
            .build()
    }
}