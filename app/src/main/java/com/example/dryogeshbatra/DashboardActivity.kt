package com.example.dryogeshbatra

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dryogeshbatra.databinding.ActivityDashboardBinding
import com.example.dryogeshbatra.firestore.FirestoreClass
import com.example.dryogeshbatra.fragments.doctorDetails.DoctorDateViewModel
import com.example.dryogeshbatra.models.UserData.User
import com.example.dryogeshbatra.utils.Constants
import com.example.dryogeshbatra.utils.GlideLoader
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.view.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.doctor_booking_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : BaseActivity(), PaymentResultWithDataListener, ExternalWalletListener,
    DialogInterface.OnClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    private lateinit var mUserDetails: User
    val viewModel: DoctorDateViewModel by viewModels()

    val database =
        Firebase.database("https://dr-batra-e6203-default-rtdb.asia-southeast1.firebasedatabase.app/").reference


    val globalDoctorSlotsAvailablity: String = "global_slots"


    val generalDoctorSlotTimining: String = "general_doctor_slot_timing"
    val specificDoctorSlotAvailablity: String = "specific_doctor_slot_timing"

    val singleUserBookedAppointmentList = "user_appointment_list"


    val slotAvailablityForSpecificDate = "slot_avaialablity_for_specific_date"
    val allUsers = "users"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPref = getSharedPreferences(Constants.LOGGED_USER_DETAILS, MODE_PRIVATE)

        val gson = Gson()
        val json: String? = sharedPref.getString(Constants.LOGGED_STRING_KEY, "")
        mUserDetails = gson.fromJson(json, User::class.java)

        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val headerView = navView.getHeaderView(0)
        val nameTextView = headerView.findViewById<TextView>(R.id.tv_name)
        val emailTextView = headerView.findViewById<TextView>(R.id.tv_email)
        val userImageView = headerView.findViewById<ImageView>(R.id.iv_user)

        nameTextView.text = mUserDetails.firstName + " " + mUserDetails.lastName
        emailTextView.text = mUserDetails.email

        GlideLoader(this@DashboardActivity).loadUserPicture(mUserDetails.image, userImageView)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.userProfileActivity, R.id.nav_chat, R.id.nav_settings, R.id.nav_appointment,
                R.id.doctorBookingFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        lifecycleScope.launchWhenCreated{

            val addDialog = AlertDialog.Builder(this@DashboardActivity)
                .setTitle("Oops...")
                .setMessage("Please switch on your internet connection.")
                .setCancelable(false)
                .create()
            viewModel.isConnected.collectLatest {
                if(it){
                   /* binding.ivWifi.setImageResource(R.drawable.ic_baseline_wifi_24)
                    binding.tvInternet.text = "Internet Available"*/
                    Log.i("InternetConnectivity", "Connected")
                    addDialog.dismiss()
                    //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }else{
                  /*  binding.ivWifi.setImageResource(R.drawable.ic_baseline_wifi_off_24)
                    binding.tvInternet.text = "Internet Not Available"*/
                    addDialog.show()

                }
            }
        }

        Checkout.preload(applicationContext)
        alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Payment Result")
        alertDialogBuilder.setCancelable(true)
        alertDialogBuilder.setPositiveButton("Ok", this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {

        try {
            lifecycleScope.launch(Dispatchers.IO){
                val userId = FirestoreClass.getCurrentUserID()
                database.child(globalDoctorSlotsAvailablity)
                    .child(viewModel.date.value?.get(0).toString())
                    .child(viewModel.date.value?.get(1).toString())
                    .child(viewModel.date.value?.get(2).toString())
                    .child(slotAvailablityForSpecificDate)
                    .push()
                    .setValue(viewModel.userBookingDetailsForDoc.value)

                database.child(allUsers).child(userId).child(singleUserBookedAppointmentList).push()
                    .setValue(viewModel.userBookingDetails.value)
            }

            alertDialogBuilder.setMessage("Payment Successful : Payment ID: $p0\nPayment Data: ${p1?.data}")
            alertDialogBuilder.show()

            viewModel.paymentStatus.postValue(true)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        try {
            alertDialogBuilder.setMessage("Payment Failed : Payment Data: ${p2?.data}")
            alertDialogBuilder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        try {
            alertDialogBuilder.setMessage("External wallet was selected : Payment Data: ${p1?.data}")
            alertDialogBuilder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        Log.i("Payment", "Payment Success")
    }

}



