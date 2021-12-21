package com.gadsag01.findhealth.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.model.HospitalBasic
import com.gadsag01.findhealth.model.HospitalFull
import com.google.maps.model.LatLng
import com.google.maps.model.PlacesSearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HospitalViewModel @Inject constructor(
    private val nearbyHospitalsSearchClient: NearbyHospitalsSearchClient) : ViewModel()
{

    val liveDataAllHospitalBasicDetails : LiveData<List<HospitalBasic>>
        get() = mutableLiveDataAllHospitalBasicDetails

    private var mutableLiveDataAllHospitalBasicDetails = MutableLiveData<List<HospitalBasic>>()

    val liveDataALlHospitalFullDetails : LiveData<List<HospitalFull>>
        get() = mutableLiveDataAllHospitalFullDetails

    private var mutableLiveDataAllHospitalFullDetails = MutableLiveData<List<HospitalFull>>()

    fun getAllHospitalsFullDetails(location : LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableLiveDataAllHospitalFullDetails.postValue(
                nearbyHospitalsSearchClient.run(location).toHospitalFullDetails())
        }
    }

    fun getAllHospitalsBasicDetails(location : LatLng) {
        Log.d("check value", location.toString())
        viewModelScope.launch(Dispatchers.IO) {
            mutableLiveDataAllHospitalBasicDetails.postValue(
                nearbyHospitalsSearchClient.run(location).toHospitalBasicDetails())
        }
    }

    fun PlacesSearchResponse.toHospitalBasicDetails() : List<HospitalBasic> {
        return this.results.toList().map {
            HospitalBasic(
                it.placeId,
                it.name,
                it.formattedAddress ?: "address not found",
                it.rating
            )
        }
    }

    fun PlacesSearchResponse.toHospitalFullDetails() : List<HospitalFull> {
        return this.results.toList().map { nearbyHospitalsSearchClient.returnHospitalFullDetails(it.placeId) }
    }


}