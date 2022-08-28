package com.example.dryogeshbatra.fragments.doctorDetails


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dryogeshbatra.adapters.TimeSlotAdapter
import com.example.dryogeshbatra.databinding.DoctorDateFragmentBinding
import com.example.dryogeshbatra.models.AvailableSlots.*
import com.example.dryogeshbatra.models.DateSlot
import kotlinx.android.synthetic.main.doctor_date_fragment.*


class DoctorDateFragment : Fragment(), TimeSlotAdapter.OnClickListener{

    lateinit var binding : DoctorDateFragmentBinding

    private lateinit var viewModel: DoctorDateViewModel

    var slotBooking: AvailableSlots? = null


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

        val args = DoctorDateFragmentArgs.fromBundle(bundle)
      /*Log.i("dorctorDateTest", args.patientDetails.firstName)
        Log.i("dorctorDateTest", args.patientDetails.userId)*/





       /* binding.cvAppointmentDate.setOnDateChangedListener { datePicker, year, month, date ->
          // Log.i("datePickeer", datePicker.)
            val dateSlot = DateSlot()
            dateSlot.date = date
            dateSlot.month = month
            dateSlot.year = year

            args.patientDetails.userBooking = dateSlot
            Log.i("dorctorDateTest", args.patientDetails.userBooking.date.toString())
            Log.i("dorctorDateTest", args.patientDetails.userBooking.month.toString())
            Log.i("dorctorDateTest", args.patientDetails.userBooking.year.toString())

        }*/
        var dayofmonth = 0
        var month = 0
        var year = 0

        dayofmonth = binding.cvAppointmentDate.dayOfMonth
        month = binding.cvAppointmentDate.month + 1
        year = binding.cvAppointmentDate.year





        val availableSlots = arrayListOf<Hour>(
            Hour(10, false, "34567yhtgfdvd", 0),
            Hour(10, false, "34567yhtgfdvd", 30),
            Hour(11, false, "34567yhtgfdvd", 0),
            Hour(11, false, "34567yhtgfdvd", 30),
            Hour(12, false, "34567yhtgfdvd", 0),
            Hour(12, false, "34567yhtgfdvd", 30),
            Hour(13, false, "34567yhtgfdvd", 0),

            )


        val avslot = arrayListOf<Year>(
                Year(2022, arrayListOf<Month>(
                Month(8, arrayListOf<Date>(
                    Date(20, availableSlots
                    )
                ))
            ))
            )



        if(true){
            val adapter = TimeSlotAdapter(requireActivity(), this, avslot, year, month, dayofmonth)
            binding.rvAppointmentSlots.adapter = adapter
        }


        binding.cvAppointmentDate.setOnDateChangedListener{datePicker, year, month, date ->
            val adapter = TimeSlotAdapter(requireActivity(), this, avslot, year, month+1, date)
            binding.rvAppointmentSlots.adapter = adapter
        }

        binding.btnBookAppointment.setOnClickListener{
            if(validateUserProfileDetails()){
                val dateSlot = DateSlot()
                dateSlot.date = binding.cvAppointmentDate.dayOfMonth
                dateSlot.month = binding.cvAppointmentDate.month + 1
                dateSlot.year = binding.cvAppointmentDate.year

                args.patientDetails.userBooking = dateSlot

                val appointmentType = if (rb_normal.isChecked) {
                    "normal"
                } else {
                    "followup"
                    //  Constants.FEMALE
                }

                val appointmentMode = if(rb_online.isChecked){
                    "online"
                }else{
                    "offline"
                }


                args.patientDetails.appointmentMode = appointmentMode
                args.patientDetails.appointmentType = appointmentType

                Log.i("modee",args.patientDetails.appointmentType)
                Log.i("modee",args.patientDetails.appointmentMode)
                Log.i("dateee", args.patientDetails.userBooking.date.toString() + ":" + args.patientDetails.userBooking.month.toString())

            }
        }

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DoctorDateViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun validateUserProfileDetails(): Boolean {
        /*val appointmentType = if (rb_normal.isChecked) {
            "normal"
        } else {
            "followup"
          //  Constants.FEMALE
        }

        val appointmentMode = if(rb_online.isChecked){
            "online"
        }else{
            "offline"
        }*/



       // val appointmentDate =
        return true
    }

    override fun onClick(position: Int) {

    }


}