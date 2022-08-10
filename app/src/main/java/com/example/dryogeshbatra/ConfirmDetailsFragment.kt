package com.example.dryogeshbatra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.dryogeshbatra.databinding.DoctorDateFragmentBinding
import com.example.dryogeshbatra.databinding.FragmentConfirmDetailsBinding
import com.example.dryogeshbatra.fragments.doctorBooking.DoctorBookingFragmentDirections
import kotlinx.android.synthetic.main.fragment_confirm_details.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConfirmDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmDetailsFragment : Fragment() {
   lateinit var binding : FragmentConfirmDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_save.setOnClickListener{
            val action =
                ConfirmDetailsFragmentDirections.actionConfirmDetailsFragmentToDoctorDateFragment()
            view.findNavController().navigate(action)
        }
    }


}