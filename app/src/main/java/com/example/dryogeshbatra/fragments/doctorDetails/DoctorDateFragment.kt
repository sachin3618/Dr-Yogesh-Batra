package com.example.dryogeshbatra.fragments.doctorDetails


import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.example.dryogeshbatra.databinding.DoctorDateFragmentBinding



class DoctorDateFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorDateFragment()
    }

    private lateinit var viewModel: DoctorDateViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DoctorDateFragmentBinding.inflate(inflater)



        return binding.root

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DoctorDateViewModel::class.java)
        // TODO: Use the ViewModel
    }



}