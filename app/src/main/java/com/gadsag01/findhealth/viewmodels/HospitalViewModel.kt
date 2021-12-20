package com.gadsag01.findhealth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.data.HospitalBasic
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
    private var mutableLiveDataSearchResponse = MutableLiveData<List<HospitalBasic>>()

    val liveDataSearchResults : LiveData<List<HospitalBasic>>
        get() = mutableLiveDataSearchResponse

    fun getHospitals(location : LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableLiveDataSearchResponse.postValue(nearbyHospitalsSearchClient.run(location).toHospitalFullDetails())
        }
    }

    fun PlacesSearchResponse.toHospitals() : List<HospitalBasic> {
        return this.results.toList().map {
            HospitalBasic(
                it.name,
                it.formattedAddress,
                it.placeId,
                it.rating
            )
        }
    }
}