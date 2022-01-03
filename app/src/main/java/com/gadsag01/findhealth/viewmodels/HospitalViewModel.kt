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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    fun syncHospitalsNearbyFlowAsync(location : LatLng): Deferred<Flow<PagingData<Hospital>>> {
        return viewModelScope.async(Dispatchers.IO) { Pager(
            PagingConfig(20)
        ) {
            HospitalPagingSource(nearbyHospitalsSearchClient, location)
        }.flow.cachedIn(viewModelScope)
        }
    }

    fun syncHospitalstoDB(location: LatLng) {
        hospitalRepository.syncHospitals(location)
    }
    fun setSelectedHospital(hospital: Hospital) {
        selectedHospitalMutableLiveData.value = hospital
    }

    val hospitalsDBFlow: Deferred<Flow<List<Hospital>>>
        get() = viewModelScope.async(Dispatchers.IO) {
            hospitalRepository.getHospitals()
        }

//    fun PlacesSearchResponse.toHospitalFullDetails() : List<HospitalFull> {
//        return this.results.toList().map { nearbyHospitalsSearchClient.returnHospitalFullDetails(it.placeId) }
//    }


}