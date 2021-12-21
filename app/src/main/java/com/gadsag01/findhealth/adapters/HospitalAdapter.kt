package com.gadsag01.findhealth.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gadsag01.findhealth.databinding.ItemHospitalBinding
import com.gadsag01.findhealth.model.HospitalBasic
import javax.inject.Inject

class HospitalAdapter @Inject constructor() :
    ListAdapter<HospitalBasic, HospitalAdapter.HospitalViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val binding = ItemHospitalBinding.inflate(
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
        private val binding: ItemHospitalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hospital: HospitalBasic) {
            with(binding) {
                hospitalName.text = hospital.name
                hospitalRating.rating = hospital.rating ?: 1f
                hospital.formattedAddress?.let {
                    hospitalAddress.text = it
                }
            }
        }
    }

    companion object Differ : DiffUtil.ItemCallback<HospitalBasic>() {
        override fun areItemsTheSame(oldItem: HospitalBasic, newItem: HospitalBasic): Boolean {
            return oldItem.placeId == newItem.placeId
        }

        override fun areContentsTheSame(oldItem: HospitalBasic, newItem: HospitalBasic): Boolean {
            return oldItem == newItem
        }
    }
}