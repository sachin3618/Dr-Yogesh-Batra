package com.example.dryogeshbatra.fragments.doctorDetails


import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dryogeshbatra.databinding.DoctorDateFragmentBinding


class DoctorDateFragment : Fragment() {


    private lateinit var viewModel: DoctorDateViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DoctorDateFragmentBinding.inflate(inflater)
        binding.calendarView.minDate = System.currentTimeMillis() - 1000;
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "ConfirmationFragment did not receive traveler information")
            return
        }

// 2
        val args = DoctorDateFragmentArgs.fromBundle(bundle)
        Log.i("dorctorDateTest", args.patientDetails.firstName)
        Log.i("dorctorDateTest", args.patientDetails.userId)

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DoctorDateViewModel::class.java)
        // TODO: Use the ViewModel
    }



}