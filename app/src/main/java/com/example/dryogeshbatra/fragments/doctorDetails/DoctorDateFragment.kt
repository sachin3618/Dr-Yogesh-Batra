package com.example.dryogeshbatra.fragments.doctorDetails


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dryogeshbatra.adapters.TimeSlotAdapter
import com.example.dryogeshbatra.databinding.DoctorDateFragmentBinding
import com.example.dryogeshbatra.models.AvailableSlots.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.sql.Time

class DoctorDateFragment : Fragment(), TimeSlotAdapter.OnClickListener {
    val viewModel: DoctorDateViewModel by viewModels()

    val database = Firebase.database("https://dr-batra-e6203-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    val globalDoctorSlotsAvailablity: String = "global_slots"


    val generalDoctorSlotTimining: String = "general_doctor_slot_timing"
    val specificDoctorSlotAvailablity: String = "specific_doctor_slot_timing"


    val slotAvailablityForSpecificDate = "slot_avaialablity_for_specific_date"

    // var adapter: TimeSlotAdapter? = null

    lateinit var binding: DoctorDateFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DoctorDateFragmentBinding.inflate(layoutInflater)
        binding.cvAppointmentDate.minDate = System.currentTimeMillis() - 1000
        binding.cvAppointmentDate.isSelected = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "ConfirmationFragment did not receive traveler information")
            return
        }

        val adapter = TimeSlotAdapter(requireActivity(), this)
        binding.rvAppointmentSlots.adapter = adapter

        /* database.child(globalDoctorSlotsAvailablity).child("2022").child("9").child("16").child(specificDoctorSlotAvailablity).push().setValue(HourForSpecificDocDate(3, 0))
        database.child(globalDoctorSlotsAvailablity).child("2022").child("9").child("16").child(specificDoctorSlotAvailablity).push().setValue(HourForSpecificDocDate(3, 30))
        database.child(globalDoctorSlotsAvailablity).child("2022").child("9").child("16").child(specificDoctorSlotAvailablity).push().setValue(HourForSpecificDocDate(4, 0))
        database.child(globalDoctorSlotsAvailablity).child("2022").child("9").child("16").child(specificDoctorSlotAvailablity).push().setValue(HourForSpecificDocDate(4, 30))
        database.child(globalDoctorSlotsAvailablity).child("2022").child("9").child("16").child(specificDoctorSlotAvailablity).push().setValue(HourForSpecificDocDate(5, 0))
*/

/*
            database.child(generalDoctorSlotTimining).push().setValue(Hour(2, 0))
            database.child(generalDoctorSlotTimining).push().setValue(Hour(2, 30))
            database.child(generalDoctorSlotTimining).push().setValue(Hour(3, 0))
            database.child(generalDoctorSlotTimining).push().setValue(Hour(3, 30))
            database.child(generalDoctorSlotTimining).push().setValue(Hour(4, 0))
            database.child(generalDoctorSlotTimining).push().setValue(Hour(5, 0))
*/




            /*      HourForSpecificDocDate(3, 0),
                 HourForSpecificDocDate(3, 30),
                 HourForSpecificDocDate(4, 0),
                 HourForSpecificDocDate(4, 30),
                 HourForSpecificDocDate(5, 0),*/

/*
         database.child(globalDoctorSlotsAvailablity).child("2022").child("9").child("16").child(slotAvailablityForSpecificDate).push().setValue(Hour(2, 0))
         database.child(globalDoctorSlotsAvailablity).child("2022").child("9").child("16").child(slotAvailablityForSpecificDate).push().setValue(Hour(2, 30))
         database.child(globalDoctorSlotsAvailablity).child("2022").child("9").child("16").child(slotAvailablityForSpecificDate).push().setValue(Hour(3, 0))
         database.child(globalDoctorSlotsAvailablity).child("2022").child("9").child("17").child(slotAvailablityForSpecificDate).push().setValue(Hour(3, 0))
    */

        //DoctorSlotForGeneral

      /*  database.child(globalDoctorSlotsAvailablity).child("2022")
            .child("9").child("16")
            .child(slotAvailablityForSpecificDate).push().setValue()*/


        viewModel.date.observe(viewLifecycleOwner) {
            database.child(generalDoctorSlotTimining)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot != null) {
                            val list = ArrayList<Hour>()
                           // val value = snapshot.getValue<ArrayList<Hour>>()
                           // Log.i("generaldoctorslottimining", value.toString())
                            for (i in snapshot.children){
                                i.getValue<Hour>()?.let { it -> list.add(it) }
                            }

                            list.let { it1 -> viewModel.generalDoctorSlotList = it1 }
                                viewModel.updateListForAdapter()
                            // viewModel.updateListForAdapter()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            //DoctorSlotForThatDay
            database.child(globalDoctorSlotsAvailablity).child(it[0].toString())
                .child(it[1].toString()).child(it[2].toString())
                .child(specificDoctorSlotAvailablity)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val list = ArrayList<HourForSpecificDocDate>()
                        for (i in snapshot.children){
                            i.getValue<HourForSpecificDocDate>()?.let { it1 -> list.add(it1) }
                        }
                       // val value = snapshot.getValue<ArrayList<HourForSpecificDocDate>>()
                       // Log.i("specificDoctorSlotAvailablity", value.toString())

                        if (list != null) {
                            viewModel.specificDoctorSlotList = list
                            viewModel.updateListForAdapter()

                            //viewModel.updateListForAdapter()
                        } else {
                            viewModel.specificDoctorSlotList.clear()
                            viewModel.updateListForAdapter()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })


            //BookedSlotsOfUserForThatDay
            database.child(globalDoctorSlotsAvailablity).child(it[0].toString())
                .child(it[1].toString()).child(it[2].toString())
                .child(slotAvailablityForSpecificDate)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val list = ArrayList<Hour>()
                        for (i in snapshot.children){
                            i.getValue<Hour>()?.let { it1 -> list.add(it1) }
                        }
                       // val value = snapshot.getValue<ArrayList<Hour>>()
                       // Log.i("slotAvailablityForSpecificDate", value.toString())

                        if (list.isNotEmpty()) {
                            viewModel.slotAvailablityForSpecificDate = list
                            viewModel.updateListForAdapter()

                            // viewModel.updateListForAdapter()
                        } else {
                            viewModel.slotAvailablityForSpecificDate.clear()
                            viewModel.updateListForAdapter()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })


        }

        /*if (viewModel.general && viewModel.specific && viewModel.available) {
            viewModel.updateListForAdapter()
        }*/





        viewModel.date.value = arrayListOf(
            binding.cvAppointmentDate.year,
            binding.cvAppointmentDate.month + 1,
            binding.cvAppointmentDate.dayOfMonth
        )
        // Log.i("year", "${binding.cvAppointmentDate.year}: ${binding.cvAppointmentDate.month}: ${binding.cvAppointmentDate.dayOfMonth}")
        binding.cvAppointmentDate.setOnDateChangedListener { datePicker, year, month, date ->
            //  Log.i("year:", "${year}:${month}:${date}")
            viewModel.date.value = arrayListOf(year, month + 1, date)
        }

        viewModel.listForAdapter.observe(viewLifecycleOwner) {
            Log.i("listofhour", it.toString())
            adapter.updateList(it)
        }

    }


    override fun onClick(position: Int) {
        Log.i("OnClickPostion", position.toString())

    }


}








