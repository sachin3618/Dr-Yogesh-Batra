package com.example.dryogeshbatra.models.UserDataForDoctor

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDataForDoc(
    var userId: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var gender: String = "",
    var age : String = "",
    var documentsId: String = "",
    var appointmentId: String = "",
    var appointmentDate: String = "",
    var appointmentTime: String = ""
) : Parcelable