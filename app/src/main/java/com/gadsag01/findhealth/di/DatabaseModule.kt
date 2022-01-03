package com.gadsag01.findhealth.di

import android.content.Context
import com.gadsag01.findhealth.adapters.HospitalListAdapter
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.data.AppDatabase
import com.gadsag01.findhealth.data.HospitalDao
import com.gadsag01.findhealth.data.HospitalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideHospitalDao(DB: AppDatabase) : HospitalDao {
        return DB.hospitalDao()
    }

    @Provides
    fun provideHospitalRepository(@ApplicationContext context: Context, hospitalDao: HospitalDao, client: NearbyHospitalsSearchClient, ) : HospitalRepository {
        return HospitalRepository(context, hospitalDao, client)
    }

    @Provides
    fun provideHospitalAdapter(client: NearbyHospitalsSearchClient) : HospitalListAdapter {
        return HospitalListAdapter(client)
    }

}