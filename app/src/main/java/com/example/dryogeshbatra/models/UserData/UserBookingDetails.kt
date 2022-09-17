package com.example.dryogeshbatra.models.UserData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserBookingDetails(
    var firstName: String = "",
    var lastName: String = "",
    var gender: String = "",
    var appointmentType: String = "",
    var appointmentMode: String = "",
    var appointmentDate : Long = 0L,
    var appointmentSlot : Long = 0L,
    var userId: String = "",
    var userBooking: DateSlot = DateSlot(),
    var paymentStatus: Boolean = false,
    var paymentTransId: String = "",
    var phoneNumber: String = ""
) : Parcelable
