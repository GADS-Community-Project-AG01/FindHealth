package com.gadsag01.findhealth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.data.HospitalRepository
import com.gadsag01.findhealth.model.Hospital
import com.google.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HospitalViewModel @Inject constructor(
    private val nearbyHospitalsSearchClient: NearbyHospitalsSearchClient,
    private val hospitalRepository: HospitalRepository
) : ViewModel() {

    val liveDataAllHospitalsNearby : LiveData<List<Hospital>>
        get() = mutableLiveDataAllHospitalsNearby

    private var mutableLiveDataAllHospitalsNearby = MutableLiveData<List<Hospital>>()

    fun getAllHospitalsNearby(location : LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableLiveDataAllHospitalsNearby.postValue(
                hospitalRepository.getAllHospitalsNearby(location)
                    ?: emptyList()
            )
        }
    }

//    fun PlacesSearchResponse.toHospitalFullDetails() : List<HospitalFull> {
//        return this.results.toList().map { nearbyHospitalsSearchClient.returnHospitalFullDetails(it.placeId) }
//    }


}