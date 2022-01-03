package com.gadsag01.findhealth

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gadsag01.findhealth.databinding.FragmentSecondBinding
import com.gadsag01.findhealth.viewmodels.HospitalViewModel
import com.gadsag01.findhealth.viewmodels.LocationViewModel
import com.gadsag01.findhealth.viewmodels.toLatLng2
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentSecondBinding? = null
    private var _googleMap: GoogleMap? = null
    private val hospitalViewModel: HospitalViewModel by activityViewModels()
    private val locationViewModel : LocationViewModel by activityViewModels()



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val googleMap get() = _googleMap!!
    private var navBarHeight: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        if (Build.VERSION.SDK_INT >=31) {requireActivity().window.setDecorFitsSystemWindows(true)}


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val mapFragment = SupportMapFragment.newInstance()
//        childFragmentManager.beginTransaction().replace(R.id.map, mapFragment).commit()
//        mapFragment.getMapAsync(this)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val bottomSheetBehavior = BottomSheetBehavior.from(
            binding.root.findViewById(R.id.bottomSheet))
//        var rect = Rect()
//        requireActivity().window.decorView.getWindowVisibleDisplayFrame(rect)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.peekHeight = 200

        hospitalViewModel.selectedHospitalLiveData.observe(viewLifecycleOwner) {

                binding.peekHospitalName.text = it.name
                it.rating?.let { binding.peekHospitalRating.rating = it }
                binding.bottomsheetHospitalName.text = it.name
                it.rating?.let { binding.bottomsheetHospitalRating.rating = it }
                binding.bottomsheetAddress.text = it.formattedAddress
                binding.bottomsheetContact.text = it.phoneNumber
                binding.bottomsheetOpeningHours.text = "Open 24hours\nSunday - Saturday"
                it.website?.let { binding.bottomsheetUrl.text = it.toString() }
                binding.bottomsheetDistance.text = "17km away"

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(p0: GoogleMap) {
        _googleMap = p0
        locationViewModel.livedataLocation.observe(viewLifecycleOwner) { location ->
            googleMap.addMarker(
                MarkerOptions()
                    .position(location.toLatLng2())
                    .title("Marker")
            )


            hospitalViewModel.selectedHospitalLiveData.observe(viewLifecycleOwner) {
                googleMap.addMarker(
                    MarkerOptions()
                        .position(it.geometry.toLatLng())
                        .title("Marker")
                )
            }
        }
    }

    fun String.toLatLng() : LatLng {
        val res = this.split(",")
        return LatLng(res.first().toDouble(), res.last().toDouble())
    }
}