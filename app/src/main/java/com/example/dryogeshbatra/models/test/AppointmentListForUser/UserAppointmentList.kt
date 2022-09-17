package com.example.dryogeshbatra.models.test.AppointmentListForUser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserAppointmentList(
    var appointment_list : ArrayList<AppointmentList> = arrayListOf()
): Parcelable