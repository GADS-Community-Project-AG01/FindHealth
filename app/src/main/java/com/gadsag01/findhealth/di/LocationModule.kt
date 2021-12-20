package com.gadsag01.findhealth.di

import android.content.Context
import com.gadsag01.findhealth.api.NearbySearchService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.maps.GeoApiContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@InstallIn(ViewModelComponent::class)
@Module
class LocationModule {

    @Provides
    fun provideFusedLocationService(@ApplicationContext context: Context) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun provideNearbySearchService(context: GeoApiContext) : NearbyHospitalsSearchClient {
        return NearbyHospitalsSearchClient(context)
    }
}
