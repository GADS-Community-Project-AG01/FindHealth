package com.gadsag01.findhealth

import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.gadsag01.findhealth.adapters.HospitalAdapter
import com.gadsag01.findhealth.adapters.HospitalListAdapter
import com.gadsag01.findhealth.databinding.FragmentFirstBinding
import com.gadsag01.findhealth.viewmodels.HospitalViewModel
import com.gadsag01.findhealth.viewmodels.LocationViewModel
import com.gadsag01.findhealth.viewmodels.toLatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

//    @Inject lateinit var client: NearbyHospitalsSearchClient

    @Inject lateinit var hospitalAdapter : HospitalAdapter
    @Inject lateinit var hospitalListAdapter: HospitalListAdapter
    private val locationViewModel : LocationViewModel by activityViewModels()
    private val hospitalViewModel: HospitalViewModel by activityViewModels()
    private val editableFactory = Editable.Factory()
    private var _binding: FragmentFirstBinding? = null
    private var permissionStatus = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.hospitalBasicRecyclerView.adapter = hospitalAdapter
        binding.hospitalBasicRecyclerView.adapter = hospitalListAdapter
        binding.hospitalBasicRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        LinearSnapHelper().attachToRecyclerView(binding.hospitalBasicRecyclerView)
        hospitalListAdapter.setVM(hospitalViewModel)

        locationViewModel.livedataLocation.observe(viewLifecycleOwner) { location ->
            Log.d("check value", location.toString())
//            hospitalViewModel.getAllHospitalsNearby(it.toLatLng())
//            viewLifecycleOwner.lifecycleScope.launch {
//                hospitalViewModel.syncHospitalsNearbyFlowAsync(location.toLatLng()).await().collectLatest {
//                    hospitalAdapter.submitData(it)
//                }
//
//            }
            hospitalViewModel.syncHospitalstoDB(location.toLatLng())
            viewLifecycleOwner.lifecycleScope.launch {
                hospitalViewModel.hospitalsDBFlow.await().asLiveData().observe(viewLifecycleOwner) {
                    hospitalListAdapter.submitList(it)
                }
            }

            binding.progressBar.visibility = View.GONE
            binding.hospitalBasicRecyclerView.visibility = View.VISIBLE
        }
//        hospitalViewModel.liveDataAllHospitalsNearby.observe(viewLifecycleOwner) {
//            hospitalAdapter.submitList(it)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.hospitalBasicRecyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        _binding = null
    }

    private fun locationObserver(location: Location) {
        Log.d("check value", location.toLatLng().toString())
    }

}