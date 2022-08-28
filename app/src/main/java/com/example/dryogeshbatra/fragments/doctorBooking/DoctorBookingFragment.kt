package com.example.dryogeshbatra.fragments.doctorBooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.dryogeshbatra.R
import kotlinx.android.synthetic.main.doctor_booking_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class DoctorBookingFragment : Fragment() {

   /* companion object {
        fun newInstance() = DoctorBookingFragment()
    }*/



    private lateinit var viewModel: DoctorBookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_booking_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* var userDetails : User? = null

        val sharedPref = this.activity!!.getSharedPreferences(
            Constants.LOGGED_USER_DETAILS,
            AppCompatActivity.MODE_PRIVATE
        )


        val gson = Gson()
        val json: String? = sharedPref.getString(Constants.LOGGED_STRING_KEY, "")
        userDetails = gson.fromJson(json, User::class.java)
*/

        btn_book_appointment.setOnClickListener {
            val action = DoctorBookingFragmentDirections.actionDoctorBookingFragmentToConfirmDetailsFragment()
            view.findNavController().navigate(action)
        }



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DoctorBookingViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return df.parse(date).time
}

}