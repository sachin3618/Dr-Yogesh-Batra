package com.example.dryogeshbatra.models.UserData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserBookingDetailsForDoc(
    var hour : Int = 0,
    var minute: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var gender: String = "",
    var appointmentType: String = "",
    var appointmentMode: String = "",
    var appointmentDay : Int = 0,
    var appointmentMonth : Int = 0,
    var appointmentYear: Int = 0,
    var userId: String = "",
    var paymentStatus: Boolean = false,
    var paymentTransId: String = "",
    var phoneNumber: String = ""
): Parcelable