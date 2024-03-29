package com.example.dryogeshbatra.fragments.doctorDetails


import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dryogeshbatra.R
import com.example.dryogeshbatra.adapters.TimeSlotAdapter
import com.example.dryogeshbatra.databinding.DoctorDateFragmentBinding
import com.example.dryogeshbatra.firestore.FirestoreClass
import com.example.dryogeshbatra.models.AvailableSlots.*
import com.example.dryogeshbatra.models.UserData.User
import com.example.dryogeshbatra.models.UserData.UserBookingDetails
import com.example.dryogeshbatra.models.UserData.UserBookingDetailsForDoc
import com.example.dryogeshbatra.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.doctor_date_fragment.*
import org.json.JSONObject


class DoctorDateFragment : Fragment(), TimeSlotAdapter.OnClickListener{

    private lateinit var auth: FirebaseAuth
    private lateinit var mUserDetails: User


    val viewModel: DoctorDateViewModel by activityViewModels()

    val database = Firebase.database("https://dr-batra-e6203-default-rtdb.asia-southeast1.firebasedatabase.app/").reference


    val globalDoctorSlotsAvailablity: String = "global_slots"


    val generalDoctorSlotTimining: String = "general_doctor_slot_timing"
    val specificDoctorSlotAvailablity: String = "specific_doctor_slot_timing"


    val slotAvailablityForSpecificDate = "slot_avaialablity_for_specific_date"
    val allUsers = "users"

    val singleUserBookedAppointmentList = "user_appointment_list"



    // var adapter: TimeSlotAdapter? = null

    lateinit var binding: DoctorDateFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DoctorDateFragmentBinding.inflate(layoutInflater)
        binding.cvAppointmentDate.minDate = System.currentTimeMillis() - 1000
        binding.cvAppointmentDate.isSelected = false

        val sharedPref = activity?.getSharedPreferences(
            Constants.LOGGED_USER_DETAILS,
            AppCompatActivity.MODE_PRIVATE
        )

        val gson = Gson()
        val json: String? = sharedPref?.getString(Constants.LOGGED_STRING_KEY, "")
        mUserDetails = gson.fromJson(json, User::class.java)


        //val button: Button = binding.btnBookAppointment

        return binding.root
    }


    private fun startPayment(mobile: String, email : String) {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity : Activity = requireActivity()
        val co = Checkout()
        try {
                var options = JSONObject()

                options.put("name","Razorpay Corp")
                options.put("description","Doctor Appointment Fees")
                //You can omit the image option to fetch the image from dashboard
                options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
                options.put("currency","INR")
                options.put("amount","100")
                options.put("send_sms_hash",true);

                val prefill = JSONObject()
                prefill.put("email",email)
                prefill.put("contact",mobile)
                options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
/*
    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        try{
            alertDialogBuilder.setMessage("Payment Successful : Payment ID: $p0\nPayment Data: ${p1?.data}")
            alertDialogBuilder.show()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        try {
            alertDialogBuilder.setMessage("Payment Failed : Payment Data: ${p2?.data}")
            alertDialogBuilder.show()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        try{
            alertDialogBuilder.setMessage("External wallet was selected : Payment Data: ${p1?.data}")
            alertDialogBuilder.show()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {

    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Firebase.database.setPersistenceEnabled(false)
        auth = Firebase.auth
        val args: DoctorDateFragmentArgs by navArgs()
        val userId = FirestoreClass.getCurrentUserID()

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
            database.child(generalDoctorSlotTimimobilening).push().setValue(Hour(4, 0))
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
                        val list = arrayListOf<HourForSpecificDocDate>()
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
                        val list = ArrayList<UserBookingDetailsForDoc>()
                        for (i in snapshot.children){
                            i.getValue<UserBookingDetailsForDoc>()?.let { it1 -> list.add(it1) }
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

        fun getAppointmentType(): String{
            if (rb_normal.isChecked){
                return "Normal"
            }else{
                return "Follow_up"
            }
        }

        fun getAppointmentMode(): String{
            if (rb_online.isChecked){
                return "Online"
            }else{
                return "Offline"
            }
        }

        btn_book_appointment.setOnClickListener{
            Log.i("positionTag", viewModel.position.toString())
            if (viewModel.position > -1){
                fun getHour(): Int{
                    val hour =  viewModel.listForAdapter.value
                    return hour?.get(viewModel.position)?.hour!!
                }

                fun getMin(): Int{
                    val min = viewModel.listForAdapter.value
                    return min?.get(viewModel.position)?.minute!!
                }

                viewModel.userBookingDetailsForDoc.postValue(
                    UserBookingDetailsForDoc(
                        viewModel.listForAdapter.value?.get(viewModel.position)!!.hour,
                        viewModel.listForAdapter.value?.get(viewModel.position)!!.minute,
                        args.patientName,
                        args.patientLastName,
                        args.patientGender,
                        getAppointmentType(),
                        getAppointmentMode(),
                        viewModel.date.value!![2],
                        viewModel.date.value!![1],
                        viewModel.date.value!![0],
                        userId,
                        true,
                        "",
                        args.patientMobile
                    )
                )

                viewModel.userBookingDetails.postValue(
                    UserBookingDetails(
                        args.patientName,
                        args.patientLastName,
                        args.patientGender,
                        getAppointmentType(),
                        getAppointmentMode(),
                        viewModel.date.value!![2],
                        viewModel.date.value!![1],
                        viewModel.date.value!![0],
                        getHour(),
                        getMin(),
                        userId,
                        true,
                        "",
                        args.patientMobile
                    )
                )

                startPayment(args.patientMobile, args.patientEmail)


                /*database.child(globalDoctorSlotsAvailablity).child(viewModel.date.value?.get(0).toString())
                    .child(viewModel.date.value?.get(1).toString()).child(viewModel.date.value?.get(2).toString())
                    .child(slotAvailablityForSpecificDate)
                    .push()
                    .setValue(UserBookingDetailsForDoc(
                        viewModel.listForAdapter.value?.get(position)!!.hour,
                        viewModel.listForAdapter.value?.get(position)!!.minute,
                        args.patientName,
                        args.patientLastName,
                        args.patientGender,
                        getAppointmentType(),
                        getAppointmentMode(),
                        viewModel.date.value!![2],
                        viewModel.date.value!![1],
                        viewModel.date.value!![0],
                        userId,
                        true,
                        "adryhs9if305",
                        args.patientMobile
                    ))

               *//* var currentUser = auth.getCurrentUser()
                Log.i("currentUser", currentUser.toString())*//*
                fun getHour(): Int{
                   val hour =  viewModel.listForAdapter.value
                    return hour?.get(position)?.hour!!
                }

                fun getMin(): Int{
                    val min = viewModel.listForAdapter.value
                    return min?.get(position)?.minute!!
                }



                database.child(allUsers).child(userId).child(singleUserBookedAppointmentList).push()
                    .setValue(UserBookingDetails(
                        args.patientName,
                        args.patientLastName,
                        args.patientGender,
                        getAppointmentType(),
                        getAppointmentMode(),
                        viewModel.date.value!![2],
                        viewModel.date.value!![1],
                        viewModel.date.value!![0],
                        getHour(),
                        getMin(),
                        userId,
                        true,
                        "adryhs9if305",
                        args.patientMobile
                    ))


*/
            }
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

        viewModel.paymentStatus.observe(viewLifecycleOwner) {
            if (it == true){
                val action = DoctorDateFragmentDirections.actionDoctorDateFragmentToNavAppointment2()
                findNavController().navigate(action)
                viewModel.paymentStatus.postValue(false)
            }
        }

    }


    override fun onClick(position: Int) {
       // Log.i("OnClickPostion", position.toString())
        viewModel.position = position
    }



}










