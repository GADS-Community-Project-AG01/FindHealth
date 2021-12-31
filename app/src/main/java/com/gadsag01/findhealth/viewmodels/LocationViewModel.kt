package com.gadsag01.findhealth.viewmodels

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class LocationViewModel @Inject constructor (
    private val locationProviderClient: FusedLocationProviderClient) : ViewModel() {

    var mutableLiveDataLocation = MutableLiveData<Location>()
    val livedataLocation : LiveData<Location>
        get() = mutableLiveDataLocation

    fun getLocation () {
        viewModelScope.launch {
            val cancellationTokenSource = CancellationTokenSource()
            val currentLocationTask = locationProviderClient.getCurrentLocation(
                PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token).await()
            mutableLiveDataLocation.postValue(currentLocationTask)
        }
    }
}

fun Location.toLatLng() : LatLng {
    return LatLng(this.latitude, this.longitude )
}

fun Location.toLatLng2() : com.google.android.gms.maps.model.LatLng {
    return com.google.android.gms.maps.model.LatLng(this.latitude, this.longitude )
}

fun LatLng.latLngAdapter() : com.google.android.gms.maps.model.LatLng {
    return com.google.android.gms.maps.model.LatLng(this.lat, this.lng )
}