package com.example.dryogeshbatra.fragments.doctorDetails


import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView.OnDateChangeListener
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dryogeshbatra.DashboardActivity
import com.example.dryogeshbatra.databinding.DoctorDateFragmentBinding
import com.example.dryogeshbatra.models.DateSlot
import com.example.dryogeshbatra.models.UserSlots
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.doctor_date_fragment.*
import kotlinx.android.synthetic.main.fragment_confirm_details.*
import java.util.*


class DoctorDateFragment : Fragment(){

    lateinit var binding : DoctorDateFragmentBinding

    private lateinit var viewModel: DoctorDateViewModel



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
// 2
        val args = DoctorDateFragmentArgs.fromBundle(bundle)
      /*  Log.i("dorctorDateTest", args.patientDetails.firstName)
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

                Log.i("modee",args.patientDetails.appointmentType )
                Log.i("modee",args.patientDetails.appointmentMode )
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




}