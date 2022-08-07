package com.example.dryogeshbatra.fragments.userAppointments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dryogeshbatra.R

class userAppointmentFragment : Fragment() {

    companion object {
        fun newInstance() = userAppointmentFragment()
    }

    private lateinit var viewModel: UserAppointmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_appointment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserAppointmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}