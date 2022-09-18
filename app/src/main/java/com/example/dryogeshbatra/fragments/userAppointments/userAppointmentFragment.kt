package com.example.dryogeshbatra.fragments.userAppointments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dryogeshbatra.R
import com.example.dryogeshbatra.adapters.TimeSlotAdapter
import com.example.dryogeshbatra.adapters.UserBookedSlotAdapter
import com.example.dryogeshbatra.databinding.DoctorDateFragmentBinding
import com.example.dryogeshbatra.databinding.FragmentUserAppointmentBinding
import com.example.dryogeshbatra.firestore.FirestoreClass
import com.example.dryogeshbatra.fragments.doctorDetails.DoctorDateViewModel
import com.example.dryogeshbatra.models.AvailableSlots.Hour
import com.example.dryogeshbatra.models.UserData.UserBookingDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class userAppointmentFragment : Fragment(), UserBookedSlotAdapter.OnClickListener {
    val database = Firebase.database("https://dr-batra-e6203-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    val singleUserBookedAppointmentList = "user_appointment_list"
    val allUsers = "users"

    //private lateinit var auth: FirebaseAuth

    val viewModel: UserAppointmentViewModel by viewModels()
    lateinit var binding: FragmentUserAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //auth = Firebase.auth
        binding = FragmentUserAppointmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = FirestoreClass.getCurrentUserID()

        val adapter = UserBookedSlotAdapter(requireActivity(), this)
        binding.recyclerView.adapter = adapter

        database.child(allUsers).child(userId).child(singleUserBookedAppointmentList)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot != null) {
                        val list = ArrayList<UserBookingDetails>()
                        val ids = ArrayList<String>()
                        // val value = snapshot.getValue<ArrayList<Hour>>()
                        // Log.i("generaldoctorslottimining", value.toString())
                        for (i in snapshot.children) {
                            i.getValue<UserBookingDetails>()?.let { it -> list.add(it) }
                            i.key?.let { ids.add(it) }
                        }

                        list.let { it1 -> viewModel.listForAdapter.postValue(it1) }
                        ids.let { it1 -> viewModel.ids.postValue(it1) }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        viewModel.listForAdapter.observe(viewLifecycleOwner){
            Log.i("listofappointments", it.toString())
            binding.noAppointments.visibility = View.GONE
            adapter.updateList(it)
        }

        /*viewModel.ids.observe(viewLifecycleOwner){
            Log.i("idsss", it.toString())
        }*/
    }

    override fun onClick(position: Int) {
        Log.i("value", position.toString())
    }


}