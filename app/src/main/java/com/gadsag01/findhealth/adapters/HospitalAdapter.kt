package com.gadsag01.findhealth.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gadsag01.findhealth.databinding.ItemHospitalBinding
import com.gadsag01.findhealth.model.Hospital
import javax.inject.Inject

class HospitalAdapter @Inject constructor() :
    PagingDataAdapter<Hospital, HospitalAdapter.HospitalViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val binding = ItemHospitalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HospitalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class HospitalViewHolder(
        private val binding: ItemHospitalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hospital: Hospital) {
            with(binding) {
                hospitalName.text = hospital.name
                hospitalRating.rating = hospital.rating ?: 1f
                hospital.formattedAddress?.let {
                    hospitalAddress.text = it
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
}

//class HospitalAdapter @Inject constructor() :
//    ListAdapter<Hospital, HospitalAdapter.HospitalViewHolder>(Differ) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
//        val binding = ItemHospitalBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return HospitalViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
//        val currentItem = getItem(position)
//        holder.bind(currentItem)
//    }
//
//    inner class HospitalViewHolder(
//        private val binding: ItemHospitalBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(hospital: Hospital) {
//            with(binding) {
//                hospitalName.text = hospital.name
//                hospitalRating.rating = hospital.rating ?: 1f
//                hospital.formattedAddress?.let {
//                    hospitalAddress.text = it
//                }
//            }
//        }
//    }
//
//    companion object Differ : DiffUtil.ItemCallback<Hospital>() {
//        override fun areItemsTheSame(oldItem: Hospital, newItem: Hospital): Boolean {
//            return oldItem.placeId == newItem.placeId
//        }
//
//        override fun areContentsTheSame(oldItem: Hospital, newItem: Hospital): Boolean {
//            return oldItem == newItem
//        }
//    }
//}