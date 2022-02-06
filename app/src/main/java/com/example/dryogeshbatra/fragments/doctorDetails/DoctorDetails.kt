package com.example.dryogeshbatra.fragments.doctorDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dryogeshbatra.R

class DoctorDetails : Fragment() {

    companion object {
        fun newInstance() = DoctorDetails()
    }

    private lateinit var viewModel: DoctorDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DoctorDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}