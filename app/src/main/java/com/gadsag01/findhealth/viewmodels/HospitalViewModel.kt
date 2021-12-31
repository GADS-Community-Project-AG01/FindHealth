package com.gadsag01.findhealth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.data.HospitalPagingSource
import com.gadsag01.findhealth.data.HospitalRepository
import com.gadsag01.findhealth.model.Hospital
import com.google.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class HospitalViewModel @Inject constructor(
    private val nearbyHospitalsSearchClient: NearbyHospitalsSearchClient,
    private val hospitalRepository: HospitalRepository
) : ViewModel() {

    val liveDataAllHospitalsNearby : LiveData<List<Hospital>>
        get() = mutableLiveDataAllHospitalsNearby

    private var mutableLiveDataAllHospitalsNearby = MutableLiveData<List<Hospital>>()

    private var  selectedHospitalMutableLiveData = MutableLiveData<Hospital>()

    val selectedHospitalLiveData: LiveData<Hospital>
        get() = selectedHospitalMutableLiveData

    fun syncHospitalsNearbyFlow(location : LatLng): Flow<PagingData<Hospital>> {
        return Pager(
            PagingConfig(20)
        ) {
            HospitalPagingSource(nearbyHospitalsSearchClient, location)
        }.flow.cachedIn(viewModelScope)
    }

    fun setSelectedHospital(hospital: Hospital) {
        selectedHospitalMutableLiveData.value = hospital
    }

//    fun PlacesSearchResponse.toHospitalFullDetails() : List<HospitalFull> {
//        return this.results.toList().map { nearbyHospitalsSearchClient.returnHospitalFullDetails(it.placeId) }
//    }


}