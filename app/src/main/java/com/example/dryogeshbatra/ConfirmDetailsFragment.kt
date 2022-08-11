package com.example.dryogeshbatra

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dryogeshbatra.databinding.FragmentConfirmDetailsBinding
import com.example.dryogeshbatra.fragments.doctorBooking.DoctorBookingFragmentDirections
import com.example.dryogeshbatra.models.DateSlot
import com.example.dryogeshbatra.models.UserBookingDetails
import com.example.dryogeshbatra.models.UserSlots
import com.example.dryogeshbatra.utils.Constants
import com.example.shopiz.models.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.doctor_booking_fragment.*
import kotlinx.android.synthetic.main.fragment_confirm_details.*


class ConfirmDetailsFragment : Fragment() {
   lateinit var binding : FragmentConfirmDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userDetails : User?
        val sharedPref = this.activity!!.getSharedPreferences(
            Constants.LOGGED_USER_DETAILS,
            AppCompatActivity.MODE_PRIVATE
        )


        val gson = Gson()
        val json: String? = sharedPref.getString(Constants.LOGGED_STRING_KEY, "")
        userDetails = gson.fromJson(json, User::class.java)

        et_patient_first_name.setText(userDetails.firstName)
        et_patient_last_name.setText(userDetails.lastName)
        et_patient_email.setText(userDetails.email)
        et_patient_mobile_number.setText(userDetails.mobile.toString())








        btn_patient_save.setOnClickListener{

           // Log.i("checkingThe", "${TextUtils.equals(et_patient_mobile_number.text.toString(), et_patient_confirm_mobile_number.text.toString())}")
            if(validateUserProfileDetails()){
                val gender = if (rb_patient_male.isChecked) {
                    Constants.MALE
                } else {
                    Constants.FEMALE
                }

                val patientUserDetails = UserBookingDetails(
                et_patient_first_name.text.toString(),
                et_patient_last_name.text.toString(),
                gender,
                "",
                "",
                0L,
                0L,
                userDetails.id,
                DateSlot(0, UserSlots(true, "Normal", "Offline", 3)),
                false,
                "3456774567",
                "234567876"
                )

                val action =
                    ConfirmDetailsFragmentDirections.actionConfirmDetailsFragmentToDoctorDateFragment(patientUserDetails)
                view.findNavController().navigate(action)
            }

        }
    }

    private fun validateUserProfileDetails(): Boolean {
        return when {

            // We have kept the user profile picture is optional.
            // The FirstName, LastName, and Email Id are not editable when they come from the login screen.
            // The Radio button for Gender always has the default selected value.

            // Check if the mobile number is not empty as it is mandatory to enter.
            TextUtils.isEmpty(et_patient_first_name.text.toString().trim { it <= ' ' }) -> {
                (activity as DashboardActivity).showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }
            TextUtils.isEmpty(et_patient_last_name.text.toString().trim { it <= ' ' }) -> {
                (activity as DashboardActivity).showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }
            TextUtils.isEmpty(et_patient_email.text.toString().trim { it <= ' ' }) -> {
                (activity as DashboardActivity).showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }
            TextUtils.isEmpty(et_patient_mobile_number.text.toString().trim { it <= ' ' }) -> {
                (activity as DashboardActivity).showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }
            TextUtils.isEmpty(et_patient_confirm_mobile_number.text.toString().trim { it <= ' ' }) -> {
                (activity as DashboardActivity).showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }
            !TextUtils.equals(et_patient_mobile_number.text.toString(), et_patient_confirm_mobile_number.text.toString()) -> {
                (activity as DashboardActivity).showErrorSnackBar("Mobile number is not matching.", true)
                false
            }
            et_patient_mobile_number.length() < 10 || et_patient_confirm_mobile_number.length() < 10 -> {
                (activity as DashboardActivity).showErrorSnackBar("Mobile Number is invalid.", true)
                false
            }
            else -> {
                true
            }
        }
    }

}