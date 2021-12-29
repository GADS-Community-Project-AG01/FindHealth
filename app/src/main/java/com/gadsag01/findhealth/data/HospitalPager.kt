package com.gadsag01.findhealth.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.model.Hospital
import com.google.maps.model.LatLng
import com.google.maps.model.PlacesSearchResponse

class HospitalPagingSource(
    private val backend: NearbyHospitalsSearchClient,
    private val query: LatLng
) : PagingSource<String, Hospital>() {

    private val keys = mutableListOf<String>()

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Hospital> {
        val response: PlacesSearchResponse
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: "Start"
            response = backend.pagedRequest(query, nextPageNumber)
            keys.add(nextPageNumber)

        } catch (e: Exception) {
            Log.e("HospitalPagingSource", e.localizedMessage ?: "An unexpected error occurred")
            return LoadResult.Error(e)

        }

        return LoadResult.Page(
            data = response.toHospital(backend),
            prevKey = params.key, // Only paging forward.
            nextKey = response.nextPageToken
        )
    }

    override fun getRefreshKey(state: PagingState<String, Hospital>): String? {
        return state.anchorPosition?.let {
            val closestPage = state.closestPageToPosition(it)
            if (keys.indexOf(closestPage?.prevKey) == -1) {
                "Start"
            } else {
                keys.last()
            }
        }
    }
}