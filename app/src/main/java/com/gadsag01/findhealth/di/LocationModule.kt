package com.gadsag01.findhealth.di

import android.content.Context
import com.gadsag01.findhealth.adapters.HospitalAdapter
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.maps.GeoApiContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class LocationModule {

    @Singleton
    @Provides
    fun provideFusedLocationService(@ApplicationContext context: Context) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideNearbySearchService(context: GeoApiContext) : NearbyHospitalsSearchClient {
        return NearbyHospitalsSearchClient(context)
    }

    @Provides
    fun provideHospitalAdapter(client: NearbyHospitalsSearchClient) : HospitalAdapter {
        return HospitalAdapter(client)
    }


}
