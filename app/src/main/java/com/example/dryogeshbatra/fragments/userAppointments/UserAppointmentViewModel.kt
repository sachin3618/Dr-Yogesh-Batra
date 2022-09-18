package com.example.dryogeshbatra.fragments.userAppointments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dryogeshbatra.models.UserData.UserBookingDetails

class UserAppointmentViewModel : ViewModel() {
    var listForAdapter = MutableLiveData<ArrayList<UserBookingDetails>>()
    var ids = MutableLiveData<ArrayList<String>>()

}