package com.example.dryogeshbatra.fragments.doctorBooking

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dryogeshbatra.R

class DoctorBookingFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorBookingFragment()
    }

    private lateinit var viewModel: DoctorBookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_booking_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DoctorBookingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}