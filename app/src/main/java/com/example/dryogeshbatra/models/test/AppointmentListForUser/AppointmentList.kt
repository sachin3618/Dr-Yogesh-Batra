package com.example.dryogeshbatra.models.test.AppointmentListForUser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppointmentList(
    var appointment_id: String = "",
    var first_name: String = "",
    var last_name : String = "",
    var gender: String = "",
    var documentsId: String = "",
    var appointmentDate: String = "",
    var appointmentTime: String = "",
    var call_id: String = "",
    var trans_id: String = ""
) : Parcelable
