package com.gadsag01.findhealth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gadsag01.findhealth.databinding.FragmentChoiceBinding
import com.gadsag01.findhealth.viewmodels.LocationViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [ChoiceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChoiceFragment : Fragment() {

    private var _binding: FragmentChoiceBinding? = null

    private val binding get() = _binding!!
    private val locationViewModel : LocationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChoiceBinding.inflate(inflater, container, false)
        requireActivity().window.navigationBarColor = getColor(requireContext(), R.color.appGrey)
        locationViewModel.getLocation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardChoice1.setOnClickListener {
            findNavController().navigate(R.id.action_choiceFragment_to_firstFragment)
        }

        binding.cardChoice2.setOnClickListener {
            Snackbar.make(it, "Emergency functions not yet implemented", 2000).show()
        }

        binding.cardChoice3.setOnClickListener {
            Snackbar.make(it, "Emergency functions not yet implemented", 2000).show()
        }
    }
}