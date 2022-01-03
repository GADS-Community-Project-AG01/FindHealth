package com.gadsag01.findhealth.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gadsag01.findhealth.R
import com.gadsag01.findhealth.api.NearbyHospitalsSearchClient
import com.gadsag01.findhealth.databinding.HospitalMainBinding
import com.gadsag01.findhealth.model.Hospital
import com.gadsag01.findhealth.utils.load
import com.gadsag01.findhealth.viewmodels.HospitalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HospitalListAdapter @Inject constructor(private val client: NearbyHospitalsSearchClient) :
    ListAdapter<Hospital, HospitalListAdapter.HospitalViewHolder>(Differ) {

    private var VM: HospitalViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val binding = HospitalMainBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HospitalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class HospitalViewHolder(
        private val binding: HospitalMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hospital: Hospital) {
            with(binding) {
                this.root.setOnClickListener {
                    VM?.setSelectedHospital(hospital)
                    it.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
                cardViewHospitalName.text = hospital.name
                cardViewRating.rating = hospital.rating ?: 1f
                hospital.formattedAddress?.let {
                    cardViewAddress.text = it
                }
                cardViewContact.text = hospital.phoneNumber
                cardViewOpeningHours.text = "Open 24hours\nSunday - Saturday"
                hospital.website?.let { cardViewUrl.text = it.toString() }
                cardViewDistance.text = "17km away"
                CoroutineScope(Dispatchers.IO).launch {
                    hospital.photoReferences?.let {
                        cardViewHospitalImage.load(client.getPhoto(it))
                    }
                }

            }
        }
    }

    companion object Differ : DiffUtil.ItemCallback<Hospital>() {
        override fun areItemsTheSame(oldItem: Hospital, newItem: Hospital): Boolean {
            return oldItem.placeId == newItem.placeId
        }

        override fun areContentsTheSame(oldItem: Hospital, newItem: Hospital): Boolean {
            return oldItem == newItem
        }
    }

    fun setVM(viewModel: HospitalViewModel) { VM = viewModel }
}